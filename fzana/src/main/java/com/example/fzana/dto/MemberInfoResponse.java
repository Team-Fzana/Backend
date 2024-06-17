package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    private String nickname;
    private String introduce;
    private String memberPhoto;
    private String email;
    private Boolean active;

    public static MemberInfoResponse createMemberinfoDto(String nickName, String introduce, String memberPhoto, String email, Boolean active) {
        return new MemberInfoResponse(nickName, introduce, memberPhoto, email, active);
    }
}
