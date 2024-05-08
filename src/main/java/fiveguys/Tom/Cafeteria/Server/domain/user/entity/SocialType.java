package fiveguys.Tom.Cafeteria.Server.domain.user.entity;

public enum SocialType {
    EMAIL, KAKAO, APPLE, GOOGLE;

    public static boolean isApple(SocialType socialType) {
        return socialType == APPLE;
    }
}
