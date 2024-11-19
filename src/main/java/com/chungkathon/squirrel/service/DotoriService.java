package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.Dotori;
import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.domain.Member;
import com.chungkathon.squirrel.repository.DotoriCollectionJpaRepository;
import com.chungkathon.squirrel.repository.DotoriJpaRepository;
import com.chungkathon.squirrel.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DotoriService {

    private final S3Service s3Service;
    private final DotoriJpaRepository dotoriRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final DotoriCollectionJpaRepository dotoriCollectionJpaRepository;

    public Dotori createDotori(MultipartFile file, DotoriCollection collection) {
        // S3에 파일 업로드
        String photoUrl = s3Service.uploadPhoto(file);

        // Dotori 엔터티 생성
        Dotori dotori = Dotori.builder()
                .photoUrl(photoUrl)
                .dotoriCollection(collection)
                .build();

        // 저장
        return dotoriRepository.save(dotori);
    }

    public List<String> createMultipleDotori(List<MultipartFile> files, DotoriCollection collection) {
        int fileCount = files.size();

        return files.stream().map(file -> {
            // S3에 파일 업로드
            String photoUrl = s3Service.uploadPhoto(file);

            // Dotori 생성
            Dotori dotori = Dotori.builder()
                    .photoUrl(photoUrl)
                    .dotoriCollection(collection)
                    .build();

            collection.addDotori(dotori);
            dotoriRepository.save(dotori);

            collection.setDotoriNum(fileCount);

            return photoUrl;
        }).collect(Collectors.toList());
    }

    // 주어진 도토리 가방과 사용자가 일치하는지
    public boolean isOwner(DotoriCollection dotoriCollection) {
        // SecurityContext에서 인증 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("로그인된 사용자가 없습니다.");
        }

        // Principal에서 사용자 이름 가져오기
        String username = authentication.getName();

        // 사용자 이름으로 Member 엔티티 조회
        Member member = memberJpaRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        // 도토리 가방의 소유자
        Member owner = dotoriCollection.getMember();

        // 체크
        return member.getId().equals(owner.getId());
    }

//    public List<Dotori> getAllActiveDotori(DotoriCollection dotoriCollection) {
//        return dotoriRepository.findAllActiveDotori(dotoriCollection);
//    }

    public void deleteDotori(Long dotori_id) {
        Dotori dotori = dotoriRepository.findById(dotori_id)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 도토리가 존재하지 않습니다."));

        DotoriCollection dotoriCollection = dotori.getDotoriCollection();

        // 도토리 삭제
        dotori.setDeleted(true);
        dotoriRepository.save(dotori);

        // 도토리 수 업데이트
        int activeDotoriCount = (int) dotoriCollection.getDotoriList().stream()
                .filter(d -> !d.isDeleted())
                .count();
        dotoriCollection.setDotoriNum(activeDotoriCount);

        // 도토리 주머니 삭제
        if (activeDotoriCount == 0) {
            dotoriCollection.setDeleted(true);
        }
        dotoriCollectionJpaRepository.save(dotoriCollection);
    }
}
