package com.practice.member.mapper;

import com.practice.member.dto.LoginDto;
import com.practice.member.dto.MemberDto;
import com.practice.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    MemberDto.Response memberToMemberResponseDto(Member member);

    List<MemberDto.Response> membersToMemberResponseDtos(List<Member> members);

    Member memberPostDtoToMember(MemberDto.Post memberPostDto);
    LoginDto.Response loginToLoginResponsDto(Member member);
}