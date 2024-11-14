package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DotoriCollection extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long dotori_collection_id;

    // @OneToMany
    // User

    @NotNull
    private String sender;

    private String message;

    @NotNull
    private boolean lock;

    @NotNull
    private int dotori_num;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Builder
    public DotoriCollection(String sender, String message, boolean lock, int dotori_num, Quiz quiz) {
        this.sender = sender;
        this.message = message;
        this.lock = lock;
        this.dotori_num = dotori_num;
        this.quiz = quiz;
    }

    public Long getId() {
        return this.dotori_collection_id;
    }

//    public Long getQuizId() {
//        return this.quiz != null ? this.quiz.getId() : null;
//        // return this.quiz.getId();
//    }

//    public Quiz getQuiz(){
//        return this.quiz;
//    }

    public void setLock(boolean b) {
        this.lock = b;
    }
}
