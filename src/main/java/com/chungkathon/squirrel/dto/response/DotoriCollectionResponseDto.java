package com.chungkathon.squirrel.dto.response;

import com.chungkathon.squirrel.domain.Quiz;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
//@AllArgsConstructor
public class DotoriCollectionResponseDto {
    private Long dotori_collection_id;
    private String sender;
    private String message;
    private boolean lock;
    private boolean deleted;
    private int dotori_num;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public DotoriCollectionResponseDto(Long dotori_collection_id, String sender, String message, boolean lock, boolean deleted, int dotori_num, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.dotori_collection_id = dotori_collection_id;
        this.sender = sender;
        this.message = message;
        this.lock = lock;
        this.deleted = deleted;
        this.dotori_num = dotori_num;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public DotoriCollectionResponseDto(Long dotori_collection_id, String sender, String message, int dotori_num, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.dotori_collection_id = dotori_collection_id;
        this.sender = sender;
        this.message = message;
        this.dotori_num = dotori_num;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}