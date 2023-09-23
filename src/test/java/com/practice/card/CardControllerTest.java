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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(CardController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerTest {
    @InjectMocks
    private CardController cardController;
    @Mock
    private CardService cardService;
    @Mock
    private CardMapper cardMapper;
    @Mock
    private JwtTokenizer jwtTokenizer;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        //MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }
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
    public void patchCardTest() throws Exception {
        // Prepare the request DTO
        CardDto.Post requestDto = new CardDto.Post();
        // Set properties of the request DTO as needed

        // Prepare a mock Card object as the result of the cardService.updateCard method
        Card mockUpdatedCard = new Card();
        // Set properties of the mockUpdatedCard as needed

        // Mock behavior for cardService.updateCard
        given(cardService.updateCard(Mockito.any(Card.class), anyLong(), anyLong())).willReturn(mockUpdatedCard);

        // Perform the PATCH request
        MvcResult result = mockMvc.perform(
                        MockMvcRequestBuilders.patch("/card/{cardId}", 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                                .content(objectMapper.writeValueAsString(requestDto))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.cardId").exists()) // Ensure that cardId exists in the response
                .andReturn();

        // You can add more assertions here to verify the response content if needed
    }
    @Test
    @DisplayName("patch to card/{cardId} test")
    public void patchCardTest2() throws Exception{
        long memberId =  1L;
        long cardId = 1L;

        CardDto.Post post = new CardDto.Post();//("cardName");
        post.setCardName("cardName");
        String postContent = gson.toJson(post);

        Card mockCard = new Card();
        mockCard.setCardId(1L);
        mockCard.setCardName("cardName");

        CardDto.Response response = cardMapper.cardToCardResponseDto(mockCard)
;       String responseContent = gson.toJson(response);

        given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);
        given(cardService.updateCard(Mockito.any(Card.class), eq(memberId),eq(cardId))).willReturn(mockCard);
        given(cardMapper.cardPostDtoToCard(Mockito.any(CardDto.Post.class))).willReturn(new Card());
        given(cardMapper.cardToCardResponseDto(Mockito.any(Card.class))).willReturn(response);
        ResultActions resultActions = mockMvc.perform(
                patch("/card/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token")
                        .content(postContent));
        resultActions.andExpect(status().isOk())//(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.cardId").value(1));//(MockMvcResultMatchers.jsonPath("$.cardId", is(1)));
    }

    @Test
    @DisplayName("get cards test")
    public void getCardsTest(){}

    @Test
    public void testGetCards() throws Exception {
        // Given
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setCardId(1L);
        Card card2 = new Card();
        card2.setCardId(2L);
        cards.add(card1);
        cards.add(card2);

        List<CardDto.Response> cardResponses = new ArrayList<>();
        cardResponses.add(cardMapper.cardToCardResponseDto(card1));
        cardResponses.add(cardMapper.cardToCardResponseDto(card2));

        BDDMockito.given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);
        BDDMockito.given(cardService.findCardsByMemberId(eq(1L))).willReturn(cards);
        BDDMockito.given(cardMapper.cardToCardResponseDto(Mockito.any(Card.class))).willAnswer(invocation -> {
            Card card = invocation.getArgument(0);
            return cardMapper.cardToCardResponseDto(card);
            //return new CardDto.Response(card);
        });

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/card")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token"));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].cardId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data[1].cardId", Matchers.is(2)));
    }

    @Test
    public void testDeleteCard() throws Exception {
        // Given
        BDDMockito.given(jwtTokenizer.getMemberId(Mockito.any(String.class))).willReturn(1L);

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.delete("/card/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer test-token"));

        // Then
        resultActions.andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
