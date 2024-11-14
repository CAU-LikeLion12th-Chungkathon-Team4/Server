package com.chungkathon.squirrel.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String username;
    private String password;
    private String nickname;
    private int squirrel_type;
}