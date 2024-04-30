package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    private String nickname;
    private String introduce;
    private String memberPhoto;

    public static MemberInfoResponse createMemberinfoDto(String nickName, String introduce, String memberPhoto) {
        return new MemberInfoResponse(nickName, introduce, memberPhoto);
    }
}
