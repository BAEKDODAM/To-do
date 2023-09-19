package com.practice.card.service;

import com.practice.card.entity.Card;
import com.practice.card.repository.CardRepository;
import com.practice.exception.BusinessLogicException;
import com.practice.exception.ExceptionCode;
import com.practice.member.entity.Member;
import com.practice.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CardService {
    private final CardRepository cardRepository;
    private final MemberService memberService;

    public CardService(CardRepository cardRepository, MemberService memberService) {
        this.cardRepository = cardRepository;
        this.memberService = memberService;
    }

    public Card createCard(Card card, long memberId){
        Member member = memberService.findVerifiedMember(memberId);
        card.setMember(member);
        System.out.println(card.getCardName()+" "+ card.getMember().getMemberId());
        return cardRepository.save(card);
    }
    public Card updateCard(Card card, long memberId, long cardId) {
        Member member = memberService.findVerifiedMember(memberId);
        Card findCard = findVerifiedCard(cardId);

        findCard.setCardName(card.getCardName());
        return cardRepository.save(findCard);
    }
    public Card findVerifiedCard(long cardId) {
        return cardRepository
                .findById(cardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CARD_NOT_FOUND));
    }
    public List<Card> findCards(long memberId) {
        List<Card> cards = findCardsByMemberId(memberId);
        cards.stream()
                .map(card -> {
                    card = findCardWithTodos(card.getCardId());
                    return card;
                }).collect(Collectors.toList());
        return cards;
    }

    public List<Card> findCardsByMemberId(long memberId) {
        List<Card> card = cardRepository.findCardsByMemberMemberId(memberId);
        System.out.println(card.get(0).getCardName());
        return card;
    }
    public Card findCardWithTodos(long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CARD_NOT_FOUND));
        return card;
        //return cardRepository.findCardWithTodos(cardId);
    }

    public void deleteCard(long memberId, long cardId){
        Card card = findVerifiedCard(cardId);

        cardRepository.delete(card);
    }
}
