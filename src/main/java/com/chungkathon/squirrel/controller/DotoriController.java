package com.chungkathon.squirrel.controller;

import com.chungkathon.squirrel.domain.Dotori;
import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.repository.DotoriCollectionJpaRepository;
import com.chungkathon.squirrel.repository.DotoriJpaRepository;
import com.chungkathon.squirrel.service.DotoriService;
import com.chungkathon.squirrel.util.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dotori")
public class DotoriController {

    private final DotoriService dotoriService;
    private final DotoriCollectionJpaRepository dotoriCollectionRepository;
    private final DotoriJpaRepository dotoriRepository;
    private final DotoriJpaRepository dotoriJpaRepository;

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
//        List<Dotori> dotoris = dotoriCollection.getDotoriList();
        List<Dotori> dotoris = dotoriJpaRepository.findAllActiveDotori(dotoriCollection);

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

//    @DeleteMapping("/delete/{dotoriId}")
//    public ResponseEntity<Map<String, String>> deleteDotoriByDotoriId(
//            @PathVariable Long dotoriId
//    ) throws IllegalAccessException {
//
//        // 도토리 찾기
//        Dotori dotori = dotoriRepository.findById(dotoriId)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid Dotori ID"));
//
//        DotoriCollection dotoriCollection = dotori.getDotoriCollection();
//        if (dotoriService.isOwner(dotoriCollection)) {
//             // 삭제
//            dotoriCollection.removeDotori(dotori);
//            dotoriRepository.delete(dotori);
//
//            // 도토리가 남지 않으면 도토리 주머니는 삭제됨
//            if (dotoriCollection.getDotoriList().isEmpty()) {
//                // Delete the DotoriCollection
//                dotoriCollectionRepository.delete(dotoriCollection);
//            }
//
////            if (dotoriCollection.getDotoriNum() == 0) {
////                dotoriCollection.setDeleted(true);
////                dotoriCollectionRepository.save(dotoriCollection);
////
////                return ResponseEntity.noContent().build();
////            }
//
//            // 성공 메시지
//            Map<String, String> response = new HashMap<>();
//            response.put("status", "success");
//            response.put("message", "도토리 삭제에 성공하였습니다.");
//
//            return ResponseEntity.ok(response);
//        }
//
//        // 실패 메시지
//        Map<String, String> response = new HashMap<>();
//        response.put("status","fail");
//        response.put("message", "도토리 삭제에 실패하였습니다. 로그인한 사용자를 확인해주세요.");
//
//        return ResponseEntity.ok(response);
//    }

//    @PutMapping("/delete/{dotori_id}")
//    public ResponseEntity<Map<String, Object>> deleteDotori(@PathVariable Long dotori_id) throws IllegalAccessException {
//        Dotori dotori = dotoriRepository.findById(dotori_id)
//                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 도토리가 존재하지 않습니다."));
//        DotoriCollection dotoriCollection = dotori.getDotoriCollection();
//
//        if (dotoriService.isOwner(dotoriCollection)) {
//
//            if (dotoriRepository.findAllActiveDotori(dotoriCollection).isEmpty()) {
//                return ResponseEntity.notFound().build();
//            }
//
//            try {
//                dotoriService.deleteDotori(dotori_id);
//
//                //성공 메시지
//                Map<String, Object> response = new HashMap<>();
//                response.put("status", "success");
//                response.put("message", "도토리 삭제에 성공하였습니다.");
//                return ResponseEntity.ok(response);
//            } catch (IllegalArgumentException e) {
//                return ResponseEntity.badRequest().build();
//            }
//
//        }
//        Map<String, Object> response = new HashMap<>();
//        response.put("status", "fail");
//        response.put("message", "도토리 삭제 권한이 없습니다.");
//        return ResponseEntity.ok(response);
//    }

    @PutMapping("/delete/{dotori_id}") public ResponseEntity<Map<String, Object>> deleteDotori(@PathVariable Long dotori_id) throws IllegalAccessException {
        // 도토리 조회
        Dotori dotori = dotoriRepository.findById(dotori_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 도토리가 존재하지 않습니다."));

        DotoriCollection dotoriCollection = dotori.getDotoriCollection();

        if (!dotoriService.isOwner(dotoriCollection)) {
            Map<String, Object> response = new HashMap<>();
            response.put("status", "fail");
            response.put("message", "도토리 삭제 권한이 없습니다."); // 반환 안됨
            return ResponseEntity.status(403).body(response);
        }

        dotoriService.deleteDotori(dotori_id);

        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("message", "도토리 삭제에 성공했습니다.");

        if (dotoriCollection.getDotoriList().isEmpty()) {
            response.put("delete", "모든 도토리가 삭제되어 도토리 주머니가 삭제되었습니다."); // 반환 안됨
        }

        return ResponseEntity.ok(response);
    }
}