package com.chungkathon.squirrel.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DotoriCollection {
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

}
