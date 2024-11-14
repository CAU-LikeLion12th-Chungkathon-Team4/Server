package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.dto.request.QuizCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.service.QuizService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/quiz")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping("/create")
    public Quiz createQuiz(@RequestBody QuizCreateRequestDto RequestDto) {
        return quizService.createQuiz(RequestDto);
    }

    @PostMapping("/{quiz_id}")
    public boolean createQuizReply(@RequestBody QuizReplyCreateRequestDto RequestDto, @PathVariable Long quiz_id) {
        return quizService.createQuizReply(RequestDto);
    }

    @GetMapping("/{quiz_id}/check")
    public boolean checkQuizReply(@PathVariable Long quiz_id, QuizReplyCreateRequestDto responseDto) {
        boolean check = quizService.checkQuizReply(quiz_id, responseDto);
        return check;
    }

}
