package com.chungkathon.squirrel.dto;

/* 왜 User 클래스를 사용하면 안 되는 것일까?
* 직렬화 기능!! (시리얼라이저 처럼...) */
import com.chungkathon.squirrel.domain.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 직렬화 기능을 가진 세션 DTO

    // 인증된 사용자 정보만 필요 => name, email, picture 필드만 선언
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}