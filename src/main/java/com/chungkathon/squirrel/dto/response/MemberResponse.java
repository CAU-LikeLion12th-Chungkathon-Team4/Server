package com.chungkathon.squirrel.dto.response;

import com.chungkathon.squirrel.domain.SquirrelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponse {
    private Long id;
    private String username;
    private String nickname;
    private SquirrelType squirrelType;
    private String urlRnd;
}