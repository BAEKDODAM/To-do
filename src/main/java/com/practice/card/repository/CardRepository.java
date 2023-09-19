package com.practice.card.repository;

import com.practice.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {//extends CrudRepository<Todo, Long> {
    //@Query("SELECT c FROM Card c LEFT JOIN FETCH c.todos WHERE c.cardId = :cardId")
    //Card findCardWithTodos(@Param("cardId") Long cardId);

    //@Query("SELECT c FROM Card c WHERE c.member.memberId = :memberId")
    //List<Card> findCardsByMemberMemberId(@Param("memberId")Long memberId);

    List<Card> findCardsByMemberMemberId(Long memberId);

    //List<Card> findCardsByMemberIdAndCardId(Long memberId, Long cardId);
}