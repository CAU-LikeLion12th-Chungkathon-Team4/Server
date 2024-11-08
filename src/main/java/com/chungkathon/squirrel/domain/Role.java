package com.chungkathon.squirrel.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/* ADMIN -> key: ROLE_ADMIN, title: 관리자
* USER -> key: ROLE_USER, title: 사용자 */

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN", "관리자"),
    USER("ROLE_USER", "사용자");

    private final String key;
    private final String title;
}