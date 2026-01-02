# 🌰 포토리

> **사진으로 추억을 주고받는 서비스, 포토리**
> 사용자는 다람쥐 캐릭터로 참여하여 사진을 공유하고,
> 퀴즈를 통해 잠긴 도토리 주머니를 열며 추억을 완성합니다.

</br>

## 📌 프로젝트 개요
<img width="1260" height="707" alt="image" src="https://github.com/user-attachments/assets/a221c0cd-b4f7-46a2-a087-9fe07eceadda" />


포토리 **URL 기반 사진 공유 + O/X 퀴즈 인터랙션**을 결합한 서비스입니다.

* 회원은 다람쥐 캐릭터를 선택하여 가입
* 사진은 **도토리 주머니** 단위로 저장
* 퀴즈를 풀어야 잠긴 사진을 확인 가능
* URL 공유를 통해 **비회원도 사진 열람 가능**

</br>

## 🛠 기술 스택

### Backend

* **Spring Boot**
* **Spring Security**
* **JWT Authentication**
* **Spring Data JPA**
* **OAuth2 (Google Login)**

### Database & Infra

* **MySQL (RDS)**
* **AWS EC2**
* **AWS S3 (사진 업로드)**
* **HTTPS 적용**

### Build & Tools

* Gradle
* GitHub Actions
* Notion (기획 & API 문서화)


</br>

## 🔐 인증 & 보안

* JWT 기반 인증
* 로그인 / 회원가입 API는 **비인증 접근 허용**
* URL 난수(`urlRnd`) 기반 접근 시 **토큰 없이도 접근 가능**
* CORS 설정 및 동적 URL 접근 이슈 해결


</br>

## 💡 주요 기능
### 1. 회원

* 일반 회원가입 / 로그인
* 다람쥐 캐릭터 선택
* 닉네임 설정
* 사용자 정보 조회


### 2. 도토리 주머니

> **사진 묶음 단위**

* 사용자별 도토리 주머니 생성
* 퀴즈 연결
* 잠금 여부 관리
* 전체 / 개별 조회


### 3. 도토리

> **실제 업로드되는 사진 단위**

* S3 이미지 업로드
* 주머니에 연결
* 개별 / 전체 조회
* 삭제 시 도토리 개수 자동 감소



### 4. 퀴즈

* O/X 퀴즈 등록
* 도토리 주머니와 1:1 연결
* 응답 결과에 따라 잠금 해제 여부 결정

</br>


## 📂 ERD 구조 (요약)

<img width="1428" height="880" alt="image" src="https://github.com/user-attachments/assets/71759b13-940a-4a38-90f7-f140a8f15cb0" />

</br></br>


## 🧩 핵심 구현 포인트

* URL 난수 기반 접근 설계 (`urlRnd`)
* 사진 업로드 시 S3 연동
* 퀴즈 정답 시 **사진 공개 + 도토리 수 증가**
* 도토리 삭제 시 **연관 데이터 자동 정리**
* JWT + SecurityConfig 세분화된 접근 제어

</br>

## 📝 커밋 히스토리 요약

* **초기**

  * Spring Boot 프로젝트 세팅
  * application.yml 구성
* **보안**

  * JWT 인증 구현
  * SecurityConfig 설정
  * Secret Key Git 히스토리 정리
* **기능**

  * 회원가입 / 로그인
  * 도토리 주머니 / 도토리 / 퀴즈 구현
* **인프라**

  * RDS 연결
  * S3 업로드
  * HTTPS 적용
* **안정화**

  * 403 / CORS / 동적 URL 이슈 해결
  * 예외 처리 및 에러 메시지 개선
