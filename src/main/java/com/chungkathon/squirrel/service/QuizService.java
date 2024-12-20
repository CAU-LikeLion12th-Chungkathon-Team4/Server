package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.domain.QuizReply;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.repository.QuizJpaRepository;
import com.chungkathon.squirrel.repository.QuizReplyJpaRepository;
import com.chungkathon.squirrel.dto.request.QuizCreateRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {
    @Autowired
    private QuizJpaRepository quizJpaRepository;
    @Autowired
    private QuizReplyJpaRepository quizReplyJpaRepository;

    public QuizService(QuizJpaRepository quizJpaRepository, QuizReplyJpaRepository quizReplyJpaRepository) {
        this.quizJpaRepository = quizJpaRepository;
        this.quizReplyJpaRepository = quizReplyJpaRepository;
    }

    @Transactional
    public Quiz createQuiz(QuizCreateRequestDto requestDto) {
        Quiz quiz = Quiz.builder()
                .question(requestDto.getQuestion())
                .answer(requestDto.getAnswer())
                .build();
        return quizJpaRepository.save(quiz);
//        return quiz;
    }

    @Transactional
    public boolean createQuizReply(QuizReplyCreateRequestDto requestDto) {
        QuizReply quizReply = QuizReply.builder()
                .reply(requestDto.getReply())
                .build();
        quizReplyJpaRepository.save(quizReply);
        return quizReply.getReply();
    }

    @Transactional
    public boolean checkQuizReply(Long quiz_id, QuizReplyCreateRequestDto requestDto) {
        Quiz quiz = quizJpaRepository.findById(quiz_id)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 퀴즈를 찾을 수 없습니다."));
        QuizReply quizReply = QuizReply.builder()
                .quiz(quiz)
                .reply(requestDto.getReply())
                .build();
        boolean isCorrect = quiz.getAnswer() == quizReply.getReply();
        return isCorrect;
    }
}
