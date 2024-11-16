package com.chungkathon.squirrel.dto.response;

import com.chungkathon.squirrel.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
//@AllArgsConstructor
public class DotoriCollectionResponseDto {
    private Long dotori_collection_id;
    private String sender;
    private String message;
//    private int dotori_num;
//    private Quiz quiz;

    public DotoriCollectionResponseDto(Long dotori_collection_id, String sender, String message) {
        this.dotori_collection_id = dotori_collection_id;
        this.sender = sender;
        this.message = message;
    }
}