package com.practice.member.service;

import com.practice.auth.utils.CustomAuthorityUtils;
import com.practice.config.SecurityConfiguration;
import com.practice.exception.BusinessLogicException;
import com.practice.exception.ExceptionCode;
import com.practice.member.entity.Member;
import com.practice.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {
    private MemberRepository memberRepository;
    private SecurityConfiguration securityConfiguration;
    private final CustomAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, SecurityConfiguration securityConfiguration, CustomAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.securityConfiguration = securityConfiguration;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member) {
        member.setPassword(securityConfiguration.passwordEncoder().encode(member.getPassword()));

        verifyExistsEmail(member.getEmail());

        // 추가: DB에 User Role 저장
        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        // 인증 이메일 전송
        //publisher.publishEvent(new MemberRegistrationApplicationEvent(savedMember));
        return savedMember;
    }
    public Member findMember(long memberId) {
        return findVerifiedMember(memberId);
    }

    public Member findVerifiedMember(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

    }
    private void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
    }
/*
    public Member findMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_EXISTS));

    }
    public Member findMemberById(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(()-> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

 */
}
