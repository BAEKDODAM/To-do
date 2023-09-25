package com.practice.member;

import com.google.gson.Gson;
import com.practice.auth.jwt.JwtTokenizer;
import com.practice.member.controller.MemberController;
import com.practice.member.dto.MemberDto;
import com.practice.member.entity.Member;
import com.practice.member.mapper.MemberMapper;
import com.practice.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @MockBean
    private MemberService memberService;
    @MockBean
    private MemberMapper memberMapper;
    @MockBean
    private JwtTokenizer jwtTokenizer;
    @InjectMocks
    private MemberController memberController;
    @Autowired
    private Gson gson;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getMemberTest() throws Exception {
        long memberId = 1L;
        String token = "Bearer test-token";
        MemberDto.Response response = MemberDto.Response.builder().memberId(memberId).name("test").email("email@test.com").memberStatus(Member.MemberStatus.MEMBER_ACTIVE).build();
        given(jwtTokenizer.getMemberId(eq(token))).willReturn(memberId);
        given(memberService.findMember(eq(memberId))).willReturn(new Member());
        given(memberMapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

        URI uri = UriComponentsBuilder.newInstance().path("/user").build().toUri();

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.get(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                );
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(memberId));
    }

    @Test
    public void postMemberTest() throws Exception {
        long memberId = 1L;
        String token = "Bearer test-token";

        MemberDto.Post post = new MemberDto.Post("test","email@test.com", "password","010-1234-1234");
        MemberDto.Response response = MemberDto.Response.builder().memberId(memberId).name("test").email("email@test.com").memberStatus(Member.MemberStatus.MEMBER_ACTIVE).build();

        given(memberService.createMember(Mockito.any(Member.class))).willReturn(new Member());
        given(memberMapper.memberPostDtoToMember(Mockito.any(MemberDto.Post.class))).willReturn(new Member());
        given(memberMapper.memberToMemberResponseDto(Mockito.any(Member.class))).willReturn(response);

        String content = gson.toJson(post);
        URI uri = UriComponentsBuilder.newInstance().path("/user/join").build().toUri();

        ResultActions actions =
                mockMvc.perform(
                        MockMvcRequestBuilders.post(uri)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", "Bearer test-token")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.memberId").value(response.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(response.getName()))
                .andExpect(jsonPath("$.data.email").value(response.getEmail()));
    }
}
