package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.dto.request.DotoriCollectionCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.service.DotoriCollectionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/dotoricollection")
public class DotoriCollectionController {
    private DotoriCollectionService dotoriCollectionService;

    public DotoriCollectionController(DotoriCollectionService dotoriCollectionService) {
        this.dotoriCollectionService = dotoriCollectionService;
    }

    @PostMapping("/create")
    public DotoriCollection createDotoriCollection(@RequestBody DotoriCollectionCreateRequestDto requestDto) {
        return dotoriCollectionService.createDotoriCollection(requestDto);
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
        return ResponseEntity.ok(quiz);
    }

    // 잠금 해제 전 퀴즈 응답
//    @PutMapping("/{dotori_collection_id}/reply") // /{dotori_collection_id}/reply
//    public boolean updateDotoriCollection(@PathVariable Long dotori_collection_id, @RequestBody QuizReplyCreateRequestDto requestDto){
//        return dotoriCollectionService.updateDotoriCollection(dotori_collection_id, requestDto);
//    }
    @PutMapping("/{dotori_collection_id}/reply")
    public ResponseEntity checkDotoriCollection(@PathVariable Long dotori_collection_id, @RequestBody QuizReplyCreateRequestDto requestDto) {
        boolean isCorrect = dotoriCollectionService.updateDotoriCollection(dotori_collection_id, requestDto);
        if (isCorrect) {
            return ResponseEntity.ok("정답입니다. 도토리 주머니의 잠금이 해제됩니다.");
        }
        else {
            dotoriCollectionService.deleteDotoriCollection(dotori_collection_id);
//            return ResponseEntity.status(200).body("틀렸습니다. 도토리 주머니가 삭제됩니다.");
            return ResponseEntity.noContent().build();
        }
    }

    // 잠금 해제 후 도토리 확인

//    @GetMapping("/{dotori_collection_id}")
//    public DotoriCollection getDotoriCollection(@PathVariable Long dotori_collection_id) {
//        return dotoriCollectionService.getDotoriCollection(dotori_collection_id);
//    }

//    @GetMapping("/{dotori_collection_id}")
//    public ResponseEntity<?> getDotoriCollection(@PathVariable Long dotori_collection_id) {
//        DotoriCollection dotoriCollection = dotoriCollectionService.getDotoriCollection(dotori_collection_id);
//
//        if (dotoriCollection.isLock()) {
//            return ResponseEntity.status(403).body("도토리 주머니가 잠겨 있습니다.");
//        }
//        return ResponseEntity.ok().body(dotoriCollection);
//    }
}
