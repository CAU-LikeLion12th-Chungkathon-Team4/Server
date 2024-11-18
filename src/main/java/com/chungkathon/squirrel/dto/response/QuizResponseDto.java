package com.chungkathon.squirrel.dto.response;

import lombok.Getter;

@Getter
public class QuizResponseDto {
    private Long quiz_id;
    private String question;
    private boolean answer;
    private String sender;

    public QuizResponseDto(Long quiz_id, String question, boolean answer, String sender) {
        this.quiz_id = quiz_id;
        this.question = question;
        this.answer = answer;
        this.sender = sender;
    }
}