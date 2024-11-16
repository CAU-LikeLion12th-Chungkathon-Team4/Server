package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "dotori")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dotori extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotNull
    private String photoUrl; // 사진 필드

    // N:1 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dotori_collection_id", nullable = false)
    private DotoriCollection dotoriCollection;

    @Builder
    public Dotori(String photoUrl, DotoriCollection dotoriCollection) {
        this.photoUrl = photoUrl;
        this.dotoriCollection = dotoriCollection;
    }

    public void setDotoriCollection(DotoriCollection dotoriCollection) {
        this.dotoriCollection = dotoriCollection;
    }
}
