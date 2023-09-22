package com.practice.member;

import com.practice.auth.utils.CustomAuthorityUtils;
import com.practice.config.SecurityConfiguration;
import com.practice.exception.BusinessLogicException;
import com.practice.member.entity.Member;
import com.practice.member.repository.MemberRepository;
import com.practice.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.swing.text.html.Option;

import java.nio.file.OpenOption;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

public class MemberServiceTest {
    @Mock
    private MemberRepository  memberRepository;
    @Mock
    private SecurityConfiguration securityConfiguration;
    @Mock
    private CustomAuthorityUtils authorityUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private MemberService memberService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        memberService = new MemberService(memberRepository, securityConfiguration, authorityUtils);
    }

    @Test
    @DisplayName("createMember test")
    public void createMemberTest(){
        Member member =  new Member();
        member.setEmail("test@example.com");
        when(securityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(member.getPassword())).thenReturn("hashedPassword");
        when(authorityUtils.createRoles(member.getEmail())).thenReturn(new ArrayList<>());
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.empty());
        when(memberRepository.save(member)).thenReturn(member);

        Member savedMember = memberService.createMember(member);

        assertNotNull(savedMember);
        assertEquals("hashedPassword", savedMember.getPassword());
        verify(memberRepository, times(1)).save(member);
    }

    @Test
    @DisplayName("findMember test")
    public void testFindMember() {
        // Arrange: Mock behavior of dependencies
        Member member = new Member();
        member.setMemberId(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Act: Call the service method
        Member foundMember = memberService.findMember(1L);

        // Assert: Verify the result
        assertNotNull(foundMember);
        assertEquals(1L, foundMember.getMemberId());
        // Add more assertions as needed
    }

    @Test
    @DisplayName("findVerifiedMember test")
    public void testFindVerifiedMember() {
        // Arrange: Mock behavior of dependencies
        Member member = new Member();
        member.setMemberId(1L);
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));

        // Act: Call the service method
        Member foundMember = memberService.findVerifiedMember(1L);

        // Assert: Verify the result
        assertNotNull(foundMember);
        assertEquals(1L, foundMember.getMemberId());
        // Add more assertions as needed
    }

    @Test
    @DisplayName("createMemberWithExistingEmail test")
    public void testCreateMemberWithExistingEmail() {
        // Arrange: Mock behavior of dependencies to simulate existing email
        Member member = new Member();
        member.setEmail("existing@example.com");
        when(securityConfiguration.passwordEncoder()).thenReturn(passwordEncoder);
        when(passwordEncoder.encode(member.getPassword())).thenReturn("hashedPassword");
        when(authorityUtils.createRoles(member.getEmail())).thenReturn(new ArrayList<>());
        when(memberRepository.findByEmail(member.getEmail())).thenReturn(Optional.of(member));

        // Act & Assert: Verify that a BusinessLogicException is thrown
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));
    }
}
