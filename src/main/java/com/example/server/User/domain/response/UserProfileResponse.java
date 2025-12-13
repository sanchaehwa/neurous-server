package com.example.server.User.domain.response;


import com.example.server.User.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {

    private String displayName;
    private String email;
    private String provider;
    private String imageFilename;
    private String imageUrl;

    public static UserProfileResponse from(User user) {
        return UserProfileResponse.builder()
                .displayName(user.getDisplayName())
                .email(user.getEmail())
                .provider(user.getProvider())
                .imageFilename(user.getImageFilename())
                .imageUrl(user.getImageUrl())
                .build();
    }

}
