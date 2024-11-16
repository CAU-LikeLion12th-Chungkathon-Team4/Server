package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Dotori;
import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.repository.DotoriCollectionJpaRepository;
import com.chungkathon.squirrel.service.DotoriService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/dotori")
public class DotoriController {

    private final DotoriService dotoriService;
    private final DotoriCollectionJpaRepository dotoriCollectionRepository;

    public DotoriController(DotoriService dotoriService, DotoriCollectionJpaRepository dotoriCollectionRepository) {
        this.dotoriService = dotoriService;
        this.dotoriCollectionRepository = dotoriCollectionRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadMultipleDotori(
            @RequestParam("files") List<MultipartFile> files,
            @RequestParam("collectionId") Long collectionId
    ) {
        // 1. DotoriCollection 조회
        DotoriCollection collection = dotoriCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid collection ID"));

        // 2. Dotori 생성 및 업로드
        List<String> uploadedPhotoUrls = dotoriService.createMultipleDotori(files, collection);

        // 3. JSON 응답 생성
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("uploadedPhotoUrls", uploadedPhotoUrls);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/{collectionId}")
    public ResponseEntity<Map<String, Object>> getDotorisByCollectionId(
            @PathVariable("collectionId") Long collectionID
    ) {
        // 도토리 가방 찾기
        DotoriCollection dotoriCollection = dotoriCollectionRepository.findById(collectionID)
                .orElseThrow(() -> new IllegalArgumentException("collectionID가 존재하지 않음"));

        // 도토리 리스트를 받아오기
        List<Dotori> dotoris = dotoriCollection.getDotoriList();

        // 도토리 데이터 반환
        List<Map<String, Object>> dotoriData = dotoris.stream().map(dotori -> {
            Map<String, Object> dotoriInfo = new HashMap<>();
            dotoriInfo.put("id", dotori.getId());
            dotoriInfo.put("photoUrl", dotori.getPhotoUrl());
            return dotoriInfo;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("dotoris", dotoriData);

        return ResponseEntity.ok(response);
    }
}