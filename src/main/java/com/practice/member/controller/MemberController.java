package com.practice.member.controller;

import com.practice.auth.jwt.JwtTokenizer;
import com.practice.member.dto.MemberDto;
import com.practice.member.entity.Member;
import com.practice.member.mapper.MemberMapper;
import com.practice.member.service.MemberService;
import com.practice.response.SingleResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public MemberController(MemberService memberService, MemberMapper mapper, JwtTokenizer jwtTokenizer) {
        this.memberService = memberService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }
    @GetMapping
    public ResponseEntity getMember(@RequestHeader("Authorization") String token) {
        log.info("[MemberController] geMemberInfo called");
        long memberId = jwtTokenizer.getMemberId(token);
        Member response = memberService.findMember(memberId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponseDto(response)), HttpStatus.OK);
    }


    @PostMapping("/join")
    public ResponseEntity postMember(@Valid @RequestBody MemberDto.Post memberPostDto) {
        Member createdMember = memberService.createMember(mapper.memberPostDtoToMember(memberPostDto));
        MemberDto.Response response = mapper.memberToMemberResponseDto(createdMember);

        return new ResponseEntity<>(new SingleResponseDto<>(response), HttpStatus.CREATED);
    }
}
