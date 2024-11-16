package com.chungkathon.squirrel.domain;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Builder
    public Member(String username, String password, String nickname, SquirrelType squirrelType, String urlRnd) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.squirrelType = squirrelType;
        this.urlRnd = urlRnd;
    }

    @Column(nullable = false)
    private String username;        // 사용자 아이디

    private String password;        // 사용자 비밀번호
    private String nickname;        // 사용자 다람쥐 닉네임

    @Enumerated(EnumType.STRING)
    private SquirrelType squirrelType;      // 사용자 다람쥐 타입

    private String urlRnd;         // 사용자 다람쥐 url

    // dotori collection과 1:N 관계
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DotoriCollection> dotoriCollections = new ArrayList<>();

    // DotoriCollection 추가 메서드
    public void addDotoriCollection(DotoriCollection dotoriCollection) {
        dotoriCollections.add(dotoriCollection);
        dotoriCollection.setMember(this);
    }

    // DotoriCollection 제거 메서드
    public void removeDotoriCollection(DotoriCollection dotoriCollection) {
        dotoriCollections.remove(dotoriCollection);
        dotoriCollection.setMember(null);
    }
}