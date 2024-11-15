package com.chungkathon.squirrel.dto.request;

import com.chungkathon.squirrel.domain.SquirrelType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
    private String username;
    private String password;
    private String nickname;
    private SquirrelType squirrelType;
}