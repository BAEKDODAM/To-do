package com.practice.card;

import com.practice.card.entity.Card;
import com.practice.card.repository.CardRepository;
import com.practice.member.entity.Member;
import com.practice.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
public class CardRepositoryTest {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveCardTest(){
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        Card card = new Card();
        card.setCardName("test");
        card.setMember(member);
        card.setTodos(null);

        Card saveCard = cardRepository.save(card);

        assertNotNull(saveCard);
        assertTrue(card.getCardName().equals("test"));
    }

    @Test
    public void findCardsByMemberMemberIdTest(){
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        Card card1 = new Card();
        card1.setCardName("test");
        card1.setMember(member);
        card1.setTodos(null);

        Card card2 = new Card();
        card2.setCardName("test2");
        card2.setMember(member);
        card2.setTodos(null);
        memberRepository.save(member);
        Card saveCard1 = cardRepository.save(card1);
        Card saveCard2 = cardRepository.save(card2);
        List<Card> findCards = cardRepository.findCardsByMemberMemberId(saveCard1.getMember().getMemberId());

        assertNotNull(findCards);
        assertTrue(findCards.size()==2);
    }

    @Test
    public void findByIdTest(){
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        Card card = new Card();
        card.setCardName("test");
        card.setMember(member);
        card.setTodos(null);

        Card saveCard = cardRepository.save(card);

        Optional<Card> findCard = cardRepository.findById(card.getCardId());

        assertTrue(findCard.isPresent());
        assertTrue(findCard.get().getCardId().equals(card.getCardId()));

    }
}
