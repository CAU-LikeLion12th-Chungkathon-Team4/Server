package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Quiz;
import com.chungkathon.squirrel.dto.request.DotoriCollectionCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizCreateRequestDto;
import com.chungkathon.squirrel.dto.request.QuizReplyCreateRequestDto;
import com.chungkathon.squirrel.repository.DotoriCollectionJpaRepository;
import com.chungkathon.squirrel.repository.QuizJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DotoriCollectionService {
    @Autowired
    private DotoriCollectionJpaRepository dotoriCollectionJpaRepository;
    @Autowired
    private QuizService quizService;
    @Autowired
    private QuizJpaRepository quizJpaRepository;

    public DotoriCollectionService(DotoriCollectionJpaRepository dotoriCollectionJpaRepository) {
        this.dotoriCollectionJpaRepository = dotoriCollectionJpaRepository;
//        this.quizService = quizService;
    }

    @Transactional
    public DotoriCollection createDotoriCollection(DotoriCollectionCreateRequestDto requestDto) {
        QuizCreateRequestDto quizRequest = new QuizCreateRequestDto();
        quizRequest.setQuestion(requestDto.getQuestion());
        quizRequest.setAnswer(requestDto.getAnswer());
        Quiz quiz = quizService.createQuiz(quizRequest);

        DotoriCollection dotoriCollection = DotoriCollection.builder()
                .sender(requestDto.getSender())
                .message(requestDto.getMessage())
                .lock(true)
                .dotori_num(Math.min(7, Math.max(1, requestDto.getDotori_num())))
                .quiz(quiz)
                .build();

//        quiz.setDotoriCollection(dotoriCollection);
        quizJpaRepository.save(quiz);
        dotoriCollectionJpaRepository.save(dotoriCollection);

        return dotoriCollection;
    }

//    @Transactional
//    public Quiz getQuiz(Long dotori_collection_id) {
//        DotoriCollection dotoriCollection = dotoriCollectionJpaRepository.findById(dotori_collection_id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 도토리 주머니가 없습니다."));
//
////        Quiz quiz = quizJpaRepository.findById(dotoriCollection.getQuizId())
////                .orElseThrow(() -> new RuntimeException("해당 ID에 연결된 퀴즈가 없습니다."));
//        return dotoriCollection.getQuiz();
//    }

    @Transactional
    public boolean updateDotoriCollection(Long dotori_collection_id, QuizReplyCreateRequestDto requestDto) {
        DotoriCollection dotoriCollection = dotoriCollectionJpaRepository.findById(dotori_collection_id)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 도토리 주머니가 없습니다."));
        boolean isCorrect = quizService.checkQuizReply(dotoriCollection.getId() , requestDto);

        if (isCorrect) {
            dotoriCollection.setLock(false);
        }
        else {
            dotoriCollectionJpaRepository.delete(dotoriCollection);
        }
        return isCorrect;
    }

    @Transactional
    public DotoriCollection getDotoriCollection(Long dotori_collection_id){
        DotoriCollection dotoriCollection = dotoriCollectionJpaRepository.findById(dotori_collection_id)
                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 도토리 주머니가 없습니다."));
//        Quiz quiz = quizJpaRepository.findById(dotoriCollection.getQuizId())
//                .orElseThrow(() -> new RuntimeException("해당 ID를 가진 퀴즈가 없습니다."));
        return dotoriCollection;
    }
}
