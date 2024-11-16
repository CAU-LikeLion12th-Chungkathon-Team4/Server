package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DotoriCollection extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long dotori_collection_id;


    @NotNull
    private String sender;

    private String message;

    @NotNull
    @Column(name = "is_locked")
    private boolean lock;

    @NotNull
    private boolean deleted;

    @NotNull
    private int dotori_num;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    // 1:N 관계 설정
    @OneToMany(mappedBy = "dotoriCollection", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dotori> dotoriList = new ArrayList<>();

    @Builder
    public DotoriCollection(String sender, String message, boolean lock, boolean deleted, int dotori_num, Quiz quiz) {
        this.sender = sender;
        this.message = message;
        this.lock = lock;
        this.deleted = deleted;
        this.dotori_num = dotori_num;
        this.quiz = quiz;
    }

    public Long getId() {
        return this.dotori_collection_id;
    }

    public String getMessage() {
        return this.message;
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

    public void setDeleted(boolean b) {
        this.deleted = b;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    // public int getDotoriNum() { return this.dotori_num; }
  
    // 도토리 추가 (양방향 관계를 고려)
    public void addDotori(Dotori dotori) {
        dotoriList.add(dotori);
        dotori.setDotoriCollection(this);
    }
}
