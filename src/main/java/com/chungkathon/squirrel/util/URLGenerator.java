package com.chungkathon.squirrel.util;

import java.util.UUID;

public class URLGenerator {
    public static String generateURL() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12); // 12자리 고유 문자열 생성
    }
}
