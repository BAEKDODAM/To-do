package com.practice.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practice.auth.jwt.JwtTokenizer;
import com.practice.card.controller.CardController;
import com.practice.card.entity.Card;
import com.practice.card.mapper.CardMapper;
import com.practice.card.service.CardService;
import com.practice.card.tdo.CardDto;
import org.aspectj.lang.annotation.Before;
import org.hamcrest.Matchers;
import org.mockito.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.google.gson.Gson;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {
    @MockBean
    private CardService cardService;
    @MockBean
    private CardMapper cardMapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("post to /card test")
    public void postCardTest() throws Exception {
        long memberId = 1L;
        Card mockCard =  new Card();
        mockCard.setCardId(1L);

        given(jwtTokenizer.getMemberId(anyString())).willReturn(memberId);
        given(cardService.createCard(Mockito.any(Card.class), eq(memberId))).willReturn(mockCard);
        given(cardMapper.cardPostDtoToCard(Mockito.any(CardDto.Post.class))).willReturn(new Card());

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.post("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization",  "Bearer test-token")
                        .content("{}")
        );

        resultActions.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", Matchers.endsWith("/cards/1")));
    }

    @Test
    @DisplayName("get cards test")
    public void testGetCards() throws Exception {
        List<Card> cards = new ArrayList<>();

        Card card1 = new Card();
        card1.setCardId(1L);
        Card card2 = new Card();
        card2.setCardId(2L);
        cards.add(card1);
        cards.add(card2);
        System.out.println(card2.getCardId()+" "+ cards.get(1).getCardId());

        List<CardDto.Response> cardResponses = List.of(
                new CardDto.Response(1L),
                new CardDto.Response(2L)
        );

        given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);
        given(cardService.findCardsByMemberId(eq(1L))).willReturn(cards);
        given(cardService.findCardWithTodos(Mockito.anyLong())).willReturn(new Card());
        given(cardMapper.cardsToCardResponseDtos(Mockito.any(List.class))).willReturn(cardResponses);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token"));

        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].cardId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].cardId", Matchers.is(2)));
    }

    @Test
    @DisplayName("patch to card/{cardId} test")
    public void patchCardTest() throws Exception {
        long cardId = 1L;
        CardDto.Post post = new CardDto.Post();
        post.setCardName("post card");
        CardDto.Response response = new CardDto.Response("post card", 1L, new ArrayList<>());

        given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);
        given(cardService.updateCard(Mockito.any(Card.class), Mockito.anyLong(), Mockito.anyLong())).willReturn(new Card());
        given(cardMapper.cardPostDtoToCard(Mockito.any(CardDto.Post.class))).willReturn(new Card());
        given(cardMapper.cardToCardResponseDto(Mockito.any(Card.class))).willReturn(response);

        Gson gson = new Gson();
        String content = gson.toJson(post);

        URI uri = UriComponentsBuilder.newInstance().path("/card/{cardId}").buildAndExpand(cardId).toUri();

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders
                                .patch(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                                .content(content));

        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.cardName").value(post.getCardName()));

    }

    @Test
    @DisplayName("delete /card/{cardId} test")
    public void deleteCardTest() throws Exception {
        given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);

        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/card/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token"));

        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
