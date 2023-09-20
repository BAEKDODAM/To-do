package com.practice.card;

import com.practice.card.entity.Card;
import com.practice.card.repository.CardRepository;
import com.practice.card.service.CardService;
import com.practice.member.entity.Member;
import com.practice.member.service.MemberService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class CardServiceTest {
    @Mock
    private CardRepository cardRepository;
    @Mock
    private MemberService memberService;
    @InjectMocks
    private CardService cardService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        cardService = new CardService(cardRepository,memberService);
    }

    @DisplayName("createCard test")
    @Test
    public void createCardTest() {
        Member fakeMember = new Member();
        fakeMember.setMemberId(1L);

        when(memberService.findVerifiedMember(1L)).thenReturn(fakeMember);

        Card card = new Card();
        card.setCardName("test card");

        when(cardRepository.save(card)).thenReturn(card);

        Card savedCard = cardService.createCard(card, 1L);
        long expectedMeberId = 1L;

        assertEquals(expectedMeberId, savedCard.getMember().getMemberId());
        verify(cardRepository, times(1)).save(card);
    }

    @Test
    @DisplayName("updateCard test")
    public void updateCardTest(){
        Member mockMember = new Member();
        mockMember.setMemberId(1L);

        when(memberService.findVerifiedMember(1L)).thenReturn(mockMember);

        Card mockCard = new Card();
        mockCard.setCardId(1L);
        mockCard.setCardName("card");

        when(cardRepository.findById(1L)).thenReturn(Optional.of(mockCard));

        Card postCard = new Card();
        postCard.setCardName("update card");

        when(cardRepository.save(any(Card.class))).thenReturn(postCard);

        Card savedCard = cardService.updateCard(postCard, 1L, 1L);
        String expectedCardName = "update card";

        assertEquals(postCard.getCardName(), savedCard.getCardName());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("findCardsByMemberId test")
    public void findCardByMemberId(){
        long memberId = 1L;
        List<Card> cards = new ArrayList<>();
        Card card1 = new Card();
        card1.setCardName("Card 1");
        cards.add(card1);

        when(cardRepository.findCardsByMemberMemberId(memberId)).thenReturn(cards);

        List<Card> result = cardService.findCardsByMemberId(memberId);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Card 1", result.get(0).getCardName());
    }

    @Test
    public void testFindCardWithTodos() {
        long cardId = 1L;
        Card card = new Card();
        card.setCardName("Test Card");
        when(cardRepository.findById(cardId)).thenReturn(java.util.Optional.of(card));

        Card result = cardService.findCardWithTodos(cardId);

        assertNotNull(result);
        assertEquals("Test Card", result.getCardName());
    }

    @Test
    public void testDeleteCard() {
        long memberId = 1L;
        long cardId = 1L;
        Card cardToDelete = new Card();
        when(cardRepository.findById(cardId)).thenReturn(Optional.of(cardToDelete));

        cardService.deleteCard(memberId, cardId);

        verify(cardRepository, times(1)).delete(cardToDelete);
    }
}
