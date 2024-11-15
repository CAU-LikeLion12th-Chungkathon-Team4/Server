package com.chungkathon.squirrel.dto.response;

import com.chungkathon.squirrel.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DotoriCollectionResponseDto {
    private Long dotori_collection_id;
    private String sender;
    private String message;
    private int dotori_num;
    private Quiz quiz;

    public DotoriCollectionResponseDto(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }
}