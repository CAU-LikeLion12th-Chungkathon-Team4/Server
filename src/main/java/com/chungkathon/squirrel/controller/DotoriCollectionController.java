package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.dto.request.DotoriCollectionCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.service.DotoriCollectionService;
import org.springframework.web.bind.annotation.*;

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
//
//    // 잠금 해제 전 퀴즈 제공
//    @GetMapping("/get/{dotori_collection_id}/quiz")
//    public Quiz getQuiz(@PathVariable Long dotori_collection_id) {
//        return dotoriCollectionService.getQuiz(dotori_collection_id);
//    }

    // 잠금 해제 전 퀴즈 응답
    @PutMapping("/update/{dotori_collection_id}/reply")
    public boolean updateDotoriCollection(@PathVariable Long dotori_collection_id, @RequestBody QuizReplyCreateRequestDto requestDto){
        return dotoriCollectionService.updateDotoriCollection(dotori_collection_id, requestDto);
    }

    // 잠금 해제 후 도토리 확인
    @GetMapping("{dotori_collection_id}")
    public DotoriCollection getDotoriCollection(@PathVariable Long dotori_collection_id) {
        return dotoriCollectionService.getDotoriCollection(dotori_collection_id);
    }
}
