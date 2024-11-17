package com.chungkathon.squirrel.dto.response;

public record TokenResponse(
        String accessToken,
        String refreshToken,
        Long id,
        String urlRnd
) {
    public static TokenResponse of(String accessToken, String refreshToken, Long id, String urlRnd) {
        return new TokenResponse(accessToken, refreshToken, id, urlRnd);
    }
}