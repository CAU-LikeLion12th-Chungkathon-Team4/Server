package com.chungkathon.squirrel.jwt;

public enum JwtValidationType {
    VALID_JWT,      // 유효
    INVALID_JWT_SIGNATURE,      // 유효 X
    INVALID_JWT_TOKEN,
    EXPIRED_JWT_TOKEN,      // 만료
    UNSUPPORTED_JWT_TOKEN,      // 지원하지 않는 토큰
    EMPTY_JWT       // 유효하지 않은 문자열
}