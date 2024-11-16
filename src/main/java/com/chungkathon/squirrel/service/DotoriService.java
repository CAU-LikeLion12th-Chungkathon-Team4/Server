package com.chungkathon.squirrel.service;

import com.chungkathon.squirrel.domain.Dotori;
import com.chungkathon.squirrel.domain.DotoriCollection;
import com.chungkathon.squirrel.repository.DotoriJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DotoriService {

    private final S3Service s3Service;
    private final DotoriJpaRepository dotoriRepository;

    public DotoriService(S3Service s3Service, DotoriJpaRepository dotoriRepository) {
        this.s3Service = s3Service;
        this.dotoriRepository = dotoriRepository;
    }

    public Dotori createDotori(MultipartFile file, DotoriCollection collection) {
        // 1. S3에 파일 업로드
        String photoUrl = s3Service.uploadPhoto(file);

        // 2. Dotori 엔터티 생성
        Dotori dotori = Dotori.builder()
                .photoUrl(photoUrl)
                .dotoriCollection(collection)
                .build();

        // 3. 저장
        return dotoriRepository.save(dotori);
    }

    public List<String> createMultipleDotori(List<MultipartFile> files, DotoriCollection collection) {

        return files.stream().map(file -> {
            // S3에 파일 업로드
            String photoUrl = s3Service.uploadPhoto(file);

            // Dotori 생성
            Dotori dotori = Dotori.builder()
                    .photoUrl(photoUrl)
                    .dotoriCollection(collection)
                    .build();

            dotoriRepository.save(dotori);

            return photoUrl;
        }).collect(Collectors.toList());
    }
}
