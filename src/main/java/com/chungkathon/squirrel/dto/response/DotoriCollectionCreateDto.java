package com.chungkathon.squirrel.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DotoriCollectionCreateDto {
    private Long id;
    private String sender;
    private String message;
    private boolean lock;
    private boolean deleted;
    private int dotoriNum;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    private MemberResponse member; // MemberDto로 변경
}
