package com.example.fzana.dto;

import com.example.fzana.domain.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
