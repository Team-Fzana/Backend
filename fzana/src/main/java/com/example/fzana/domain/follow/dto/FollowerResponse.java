package com.example.fzana.domain.follow.dto;

import com.example.fzana.domain.follow.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FollowerResponse {
    private Long followerId;
    private String name;
    private Boolean isActive;
    private String memberPhoto;

    public static FollowerResponse createMember(Follow f) {
        return new FollowerResponse(
                f.getFollower().getId(),
                f.getFollower().getNickName(),
                f.getFollower().getActive(),
                f.getFollower().getMemberPhoto()
        );
    }
}
