package com.chungkathon.squirrel.dto.response;

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
    private int squirrel_type;
    private String urlRnd;
}