package com.chungkathon.squirrel.dto.request;

import com.chungkathon.squirrel.domain.Quiz;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DotoriCollectionCreateRequestDto {
    private String sender;
    private String message;
    private boolean lock;
    private Quiz quiz;

    public String getSender() {
        return this.sender;
    }

    public String getMessage() {
        return this.message;
    }

    public String getQuestion() {
        return this.quiz.getQuestion();
    }

    public boolean getAnswer() {
        return this.quiz.getAnswer();
    }

//    public boolean setLock(boolean b) {
//        this.lock = b;
//        return this.lock;
//    }

//    public Quiz getQuiz() {
//        return this.quiz;
//    }
}
