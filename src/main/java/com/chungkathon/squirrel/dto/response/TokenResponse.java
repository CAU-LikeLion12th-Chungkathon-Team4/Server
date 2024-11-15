package com.chungkathon.squirrel.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long id
) {
    public static TokenResponse of(String accessToken, String refreshToken, Long id) {
        return new TokenResponse(accessToken, refreshToken, id);
    }
}