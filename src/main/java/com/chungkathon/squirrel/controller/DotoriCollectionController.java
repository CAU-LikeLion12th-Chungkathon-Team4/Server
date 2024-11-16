package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.dto.request.DotoriCollectionCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.dto.response.DotoriCollectionResponseDto;
import com.chungkathon.squirrel.dto.response.QuizResponseDto;
import com.chungkathon.squirrel.repository.DotoriCollectionJpaRepository;
import com.chungkathon.squirrel.service.DotoriCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
@RestController
@RequestMapping("/dotoricollection")
public class DotoriCollectionController {
    private final DotoriCollectionJpaRepository dotoriCollectionJpaRepository;
    private DotoriCollectionService dotoriCollectionService;

    public DotoriCollectionController(DotoriCollectionService dotoriCollectionService, DotoriCollectionJpaRepository dotoriCollectionJpaRepository) {
        this.dotoriCollectionService = dotoriCollectionService;
        this.dotoriCollectionJpaRepository = dotoriCollectionJpaRepository;
    }

    // 도토리 주머니 생성
    @PostMapping("/create/{urlRnd}")
    public DotoriCollection createDotoriCollection(@PathVariable String urlRnd, @RequestBody DotoriCollectionCreateRequestDto requestDto) {
        return dotoriCollectionService.createDotoriCollection(urlRnd, requestDto);
    }

    // 잠금 해제 전 퀴즈 제공

//    @GetMapping("/get/{dotori_collection_id}/quiz") // /{dotori_collection_id}/quiz
//    public Quiz getQuiz(@PathVariable Long dotori_collection_id) {
//        return dotoriCollectionService.getQuiz(dotori_collection_id);
//    }

    @GetMapping("/{dotori_collection_id}/quiz")
    public ResponseEntity<?> getQuizByDotoriCollectionId(@PathVariable Long dotori_collection_id) {
        DotoriCollection dotoriCollection = dotoriCollectionService.getDotoriCollection(dotori_collection_id);
        Quiz quiz = dotoriCollection.getQuiz();

        if (quiz == null) {
            return ResponseEntity.status(404).body("연결된 퀴즈를 찾을 수 없습니다.");
        }
        QuizResponseDto responseDto = new QuizResponseDto(
                quiz.getId(),
                quiz.getQuestion(),
                quiz.getAnswer()
        );

        return ResponseEntity.ok().body(responseDto);
    }

    // 잠금 해제 전 퀴즈 응답
    @PutMapping("/{dotori_collection_id}/reply")
    public ResponseEntity checkDotoriCollection(@PathVariable Long dotori_collection_id, @RequestBody QuizReplyCreateRequestDto requestDto) {
        boolean isCorrect = dotoriCollectionService.updateDotoriCollection(dotori_collection_id, requestDto);
        if (isCorrect) {
            return ResponseEntity.ok("정답입니다. 도토리 주머니의 잠금이 해제되었습니다.");
        }
        else {
            return ResponseEntity.status(200).body("틀렸습니다. 도토리 주머니가 삭제되었습니다."); // return ResponseEntity.noContent().build();
        }
    }

    // 도토리 주머니 열어보기
    @GetMapping("/{dotori_collection_id}")
    public ResponseEntity<?> getDotoriCollection(@PathVariable Long dotori_collection_id) {
        DotoriCollection dotoriCollection = dotoriCollectionService.getDotoriCollection(dotori_collection_id);

        if (dotoriCollection == null) {
            return ResponseEntity.status(404).body("도토리 주머니를 찾을 수 없습니다.");
        }

        if (dotoriCollection.isDeleted()){
            return ResponseEntity.status(403).body("삭제된 도토리 주머니에 접근할 수 없습니다.");
        }

        if (dotoriCollection.isLock()) {
            return ResponseEntity.status(403).body("도토리 주머니가 잠겨 있습니다.");
        }

        DotoriCollectionResponseDto responseDto = new DotoriCollectionResponseDto(
                dotoriCollection.getId(),
                dotoriCollection.getSender(),
                (String) dotoriCollection.getMessage()
        );

        return ResponseEntity.ok().body(responseDto);
    }
}
