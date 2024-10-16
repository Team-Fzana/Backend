package com.example.fzana.domain.member.dto;

import com.example.fzana.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberListResponse {
    private Long userId;
    private String name;
    private String introduce;
    private String memberPhoto;
    private Boolean active;

    // DB에서 가져온 엔티티를 DTO로 반환하는 메서드
    public static MemberListResponse createMember(Member member) {
        return new MemberListResponse(
                member.getId(),
                member.getNickName(),
                member.getIntroduce(),
                member.getMemberPhoto(),
                member.getActive());

    }
}
