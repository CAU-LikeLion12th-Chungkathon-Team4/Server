package com.chungkathon.squirrel.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public enum SquirrelType {
    ASTRONAUT_SQU("astronautSqu"),
    CAU_CLOTH_SQU("cauClothSqu"),
    CONSTRUCT_SQU("constructSqu"),
    DEFAULT_SQU("defaultSqu"),
    LUCK_SQU("luckSqu"),
    PROGRAMMER_SQU("programmerSqu"),
    PUANG_SQU("puangSqu"),
    SANTA_SQU("santaSqu"),
    SHEEP_SQU("sheepSqu"),
    SKRR_SQU("skrrSqu"),
    TUBE_SQU("tubeSqu");

    private final String type;

    SquirrelType(String type) {
        this.type = type;
    }

    @JsonValue
    public String getType() {
        return type;
    }

    @JsonCreator
    public static SquirrelType from(String type) {
        for (SquirrelType value : SquirrelType.values()) {
            if (value.type.equals(type)) { // 요청값과 정확히 일치해야 함
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid squirrelType: " + type);
    }
}
