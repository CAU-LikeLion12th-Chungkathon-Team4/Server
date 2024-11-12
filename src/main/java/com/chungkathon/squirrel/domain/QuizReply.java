package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuizReply {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long quiz_reply_id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @NotNull
    private boolean reply;

    @Builder
    public QuizReply(Quiz quiz, boolean reply) {
        this.quiz = quiz;
        this.reply = reply;
    }

    public boolean getReply(){
        return this.reply;
    }
}
