package com.chungkathon.squirrel.dto.response;

import lombok.Getter;

@Getter
public class QuizResponseDto {
    private Long quiz_id;
    private String question;
    private boolean answer;

    public QuizResponseDto(Long quiz_id, String question, boolean answer) {
        this.quiz_id = quiz_id;
        this.question = question;
        this.answer = answer;
    }
}