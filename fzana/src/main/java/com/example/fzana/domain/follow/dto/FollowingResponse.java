package com.example.fzana.domain.follow.dto;

import com.example.fzana.domain.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowingResponse {
    private Long followingId;
    private String name;
    private Boolean isActive;
    private String memberPhoto;

    public static FollowingResponse createMember(Follow f) {
        return new FollowingResponse(
                f.getFollowing().getId(),
                f.getFollowing().getNickName(),
                f.getFollowing().getActive(),
                f.getFollowing().getMemberPhoto()
        );
    }
}