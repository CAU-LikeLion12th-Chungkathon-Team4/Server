package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Quiz {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long quiz_id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dotori_collection_id")
    private DotoriCollection dotoriCollection;

    @NotNull
    private String question;

    @NotNull
    private boolean answer;

    @Builder
    public Quiz(String question, boolean answer) {
        this.question = question;
        this.answer = answer;
    }


    public Long getId() {
        return this.quiz_id;
    }

    public String getQuestion() {
        return this.question;
    }

    public boolean getAnswer() {
        return this.answer;
    }

//    public void setDotoriCollection(DotoriCollection dotoriCollection) {
//        this.dotoriCollection = dotoriCollection;
//    }

//    public void setDotoriCollection(DotoriCollection dotoriCollection) {
//        this.dotoriCollection = dotoriCollection;
//    }
}
