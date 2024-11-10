package com.chungkathon.squirrel.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
public class UserInfo {
    private String email;
    private String name;
    private String picture;

    public UserInfo(String email, String name, String picture) {
        this.email = email;
        this.name = name;
        this.picture = picture;
    }
}
