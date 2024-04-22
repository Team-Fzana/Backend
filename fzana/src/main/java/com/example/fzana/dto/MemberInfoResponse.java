package com.example.fzana.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {
    private String nickname;
    private String introduce;

    public static MemberInfoResponse createMemberinfoDto(String nickName, String introduce) {
        return new MemberInfoResponse(nickName, introduce);
    }
}
