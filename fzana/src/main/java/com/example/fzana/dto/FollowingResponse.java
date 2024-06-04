package com.example.fzana.dto;

import com.example.fzana.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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