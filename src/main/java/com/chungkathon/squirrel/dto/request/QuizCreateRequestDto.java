package com.chungkathon.squirrel.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class QuizCreateRequestDto {
    private String question;
    private boolean answer;

    public String getQuestion() {
        return question;
    }

    public boolean getAnswer() {
        return answer;
    }
}