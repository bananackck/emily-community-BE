# Community FE

Vanilla JavaScript + Spring Boot + MySQL 로 만든 **로컬 커뮤니티 게시판** 프로젝트입니다. 로그인, 회원가입, 게시글 CRUD, 댓글 작성, 프로필·비밀번호 수정, 회원 탈퇴 기능을 제공합니다.
<br> 프론트엔드 파일입니다.
---

## 📌 기술 스택

- **Backend**
    <div style="display: flex; gap: 10px; flex-wrap: wrap; ">
        <img style="height: 25px;" alt="java" src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white">
        <img style="height: 25px;" src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
    </div>
  <br>
- **Frontend**
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
        <img style="height: 25px;" src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">
        <img style="height: 25px;" src="https://img.shields.io/badge/html5-E34F26?style=for-the-badge&logo=html5&logoColor=white">
        <img style="height: 25px;" src="https://img.shields.io/badge/css-1572B6?style=for-the-badge&logo=css3&logoColor=white">
    </div>
  <br>
- **Database**
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
        <img style="height: 25px;" src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
    </div>
  <br>
- **빌드 도구**
    <div style="display: flex; gap: 10px; flex-wrap: wrap;">
          <img style="height: 25px;" src="https://img.shields.io/badge/gradle-02303A?style=for-the-badge&logo=gradle&logoColor=white">
    </div>

---

## 🚀 주요 페이지

![로그인](https://github.com/user-attachments/assets/b3875818-7d69-44ee-bca9-6767666d039f)
![게시판](https://github.com/user-attachments/assets/255d5b07-527b-4085-a028-52ec54842227)

상세한 기능 조작 동영상은 아래 링크를 참고해주세요.

https://youtu.be/Toc_h0OYZ7E

---

## ⚙️ 실행 환경 요구사항

- Java 17 이상
- Gradle 7.0 이상
- VSCode Live Server
- MySQL Server 8 이상

---

## 🏗️ 로컬 설치 & 실행

### 1️⃣ 리포지토리 클론

```bash
git clone https://github.com/bananackck/emily-community-BE.git
cd community-clone
```

### 2️⃣ MySQL 설정 및 웹 서버 실행

```sql
CREATE DATABASE community_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

`src/main/resources/application.properties` 에 DB 접속 정보 입력

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/community_db?useSSL=false&serverTimezone=Asia/Seoul
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD
```

### 3️⃣ jwt 시크릿 키 설정
`src/main/resources/application-secret.properties` 에 jwt 시크릿 키 입력
```properties
jwt.secret=###########################
```

### 4️⃣ 백엔드 실행

```bash
./gradlew bootRun
```

▶ [http://localhost:8080](http://localhost:8080)

### 5️⃣ 프론트엔드 실행

VSCode → `src/pages` 폴더에서 **Live Server** 실행
▶ [http://127.0.0.1:5500/src/pages/login.html](http://127.0.0.1:5500/src/pages/login.html)

---

## 📂 백엔드 프로젝트 구조

```
emily-community-BE/
├── assets
│   └── img
│       └── data
│           └── profile-basic.png
└── main
    ├── java
    │   └── com.bananackck.community_1
    │       ├── Community1Application.java
    │       ├── _feature
    │       │   ├── auth
    │       │   ├── comment
    │       │   ├── post
    │       │   ├── postlike
    │       │   └── user
    │       ├── config
    │       ├── exception
    │       └── util
    └── resources

```
---
## 🔗 API 요약

| 기능        | HTTP 메서드 / 엔드포인트                | 설명            |
| --------- | ------------------------------- | ------------- |
| 회원가입      | POST `/api/auth/signup`         | 새 계정 생성       |
| 로그인       | POST `/api/auth/login`          | JWT 발급        |
| 게시글 목록 조회 | GET `/api/posts`                | 전체 게시글 리스트    |
| 게시글 상세 조회 | GET `/api/posts/{id}`           | 단일 게시글 + 댓글   |
| 게시글 작성    | POST `/api/posts`               | 제목·내용·이미지 업로드 |
| 게시글 수정    | PATCH `/api/posts/{id}`         | 내가 쓴 게시글 수정   |
| 게시글 삭제    | DELETE `/api/posts/{id}`        | 내가 쓴 게시글 삭제   |
| 댓글 작성     | POST `/api/posts/{id}/comments` | 댓글 추가         |
| 프로필 수정    | PATCH `/api/users/me`           | 닉네임·프로필 사진 변경 |
| 비밀번호 변경   | PATCH `/api/users/me/password`  | 비밀번호 업데이트     |
| 회원 탈퇴     | DELETE `/api/users/me`          | 계정 삭제         |


---

## 🤝 기여

Pull Request 환영합니다. 이 프로젝트는 개인 학습용으로 제작되었습니다.

---

## 📄 기타

- **회고** : https://www.notion.so/bananackck/Community-1c3cf2b6ab9380c8b5b2c30e0b0991e6?pvs=4
- **주인장 블로그** : https://bananackck.tistory.com
