package com.practice.member;

import com.practice.member.entity.Member;
import com.practice.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void saveMemberTest(){
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        Member savedMember = memberRepository.save(member);

        assertNotNull(savedMember);
        assertTrue(member.getEmail().equals(savedMember.getEmail()));
        assertTrue(member.getName().equals(savedMember.getName()));
        assertTrue(member.getPhone().equals(savedMember.getPhone()));
        assertTrue(member.getPassword().equals(savedMember.getPassword()));
        assertTrue(member.getMemberStatus().equals(savedMember.getMemberStatus()));
    }

    @Test
    public void findByEmailTest() {
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        assertTrue(findMember.isPresent());
        assertTrue(findMember.get().getEmail().equals(member.getEmail()));
    }

    @Test
    public void findByIdTest(){
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPassword("password");
        member.setMemberStatus(Member.MemberStatus.MEMBER_ACTIVE);
        member.setPhone("010-1111-2222");

        member = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findById(member.getMemberId());

        assertTrue(findMember.isPresent());
        assertTrue(findMember.get().getMemberId().equals(member.getMemberId()));
    }
}
