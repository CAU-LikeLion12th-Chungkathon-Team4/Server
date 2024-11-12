package com.chungkathon.squirrel.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuizReplyCreateRequestDto {
    private boolean reply;

    public boolean getReply() {
        return reply;
    }
}
