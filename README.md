# login-api

# 요구사항
- 회원가입
- 로그인
- 인증상태를 알려주는 api
- 서버가 여러대일 때를 가정

# 구현 기술 stack
- H2 DB
- myBatis
- Spring Boot

# 문제해결 키워드
- Session Clustering
- 사용자 로그인 정보
  - DB에 저장
  - 캐시에 저장
- JWT

# trouble shooting
- lombok 어노테이션 적용 안되는 문제
  - annotationProcessor 'org.projectlombok:lombok' 을 추가해야 컴파일러가 어노테이션에도 롬복을 적용함
  - spring은 POJO 객체를 다루므로 Getter가 필수적이다. lombok으로 getter를 적용
  - https://insanelysimple.tistory.com/106
- mapper xml 파일 경로 못찾는 문제
  - mapper 파일에 확장자(.xml)를 안붙여서 발생한 오류
- snake case인 table과 camel case인 class 매핑 안되는 문제
  - mapUnderscoreToCamelCase 옵션 true로 설정해서 해결
- 세션은 특정 브라우저마다 고유하고 A브라우저가 다른 B, C, D 브라우저의 세션 정보를 참조할 수 없다
    - HttpSessionContext.getIds()라는 기능이 있지만 보안 이슈로 deprecated됨
- 분산 서버 환경에서 인증 정보 공유 문제
    - 서버에 저장된 로그인 정보가 다르므로 모든 서버가 공유하는 session storage로 해결
- 같은 아이디로 서로 다른 브라우저에서 동시 접속 처리시 문제
    - session storage는 모든 서버가 로그인 정보를 공유하므로 이를 해결

# 세션 & 쿠키를 이용한 로그인 처리

## 회원가입 테스트
![스크린샷 2022-03-05 오후 4 49 25](https://user-images.githubusercontent.com/66231761/156874049-12d37195-a621-4d03-adbe-ed817e6f8623.png)  
![스크린샷 2022-03-05 오후 4 49 51](https://user-images.githubusercontent.com/66231761/156874065-d2e5f6b1-c055-47cf-905b-378e0ed2ff50.png)  
- 회원가입 성공  

## 로그인 테스트
![스크린샷 2022-03-05 오후 4 50 23](https://user-images.githubusercontent.com/66231761/156874088-7c70fc57-0619-4908-97f6-53d9afa6a8d0.png)  
![스크린샷 2022-03-05 오후 4 50 37](https://user-images.githubusercontent.com/66231761/156874107-0e4f87b0-395f-4c49-92c8-b72b27ae8494.png)  
- 브라우저가 서버에 요청시 세션이 자동 생성되고 고유한 sessionId가 생성되서 JSESSIONID라는 쿠키에 담김

## 로그아웃 실패 테스트
- 로그인 때 생성된 sessionID를 쿠키에 담고 로그아웃 요청을 보냄
![스크린샷 2022-03-05 오후 4 51 09](https://user-images.githubusercontent.com/66231761/156874128-083f8d84-aa72-4d24-86b4-e3128d44f064.png)  

![스크린샷 2022-03-05 오후 4 51 23](https://user-images.githubusercontent.com/66231761/156874137-5a6576f4-b354-42c6-9553-58771c9d6e48.png)  

![스크린샷 2022-03-05 오후 4 51 37](https://user-images.githubusercontent.com/66231761/156874148-8cef71c6-c9ab-4746-bc46-2ff611aa64e8.png)  
- 세션 유효 시간을 1초로 세팅했기 때문에 이미 로그아웃 상태에 로그아웃 요청을 받았으므로 의도한대로 DuplicateRequestException이 발생함

## 로그아웃 성공 테스트
![스크린샷 2022-03-05 오후 4 52 15](https://user-images.githubusercontent.com/66231761/156874173-a5f3044c-e7b5-455c-b64b-7f430cdfb6d5.png)  
![스크린샷 2022-03-05 오후 4 52 26](https://user-images.githubusercontent.com/66231761/156874186-a828aba5-54ae-4616-9979-64bc632e0833.png)  
- 세션 유효 시간을 600초로 설정했을 때는 로그아웃이 성공하는 모습

# 세션 & 쿠키 방식의 한계

- 현재 클라이언트-서버의 세션 정보만 접근할 수 있기 때문에 현재 접속중인 유저 외의 다른 유저의 로그인 정보를 조회할 수 없다
- 분산 서버 환경에서 서버마다 세션에 저장된 로그인 정보가 각자 다르기 때문에 분산 서버 환경에 맞지 않음

# Ref

- [https://velog.io/@meme2367/MindDiary-이슈-10.-다중-서버에서-인증-정보는-어디에-저장할까-JWT-토큰](https://velog.io/@meme2367/MindDiary-%EC%9D%B4%EC%8A%88-10.-%EB%8B%A4%EC%A4%91-%EC%84%9C%EB%B2%84%EC%97%90%EC%84%9C-%EC%9D%B8%EC%A6%9D-%EC%A0%95%EB%B3%B4%EB%8A%94-%EC%96%B4%EB%94%94%EC%97%90-%EC%A0%80%EC%9E%A5%ED%95%A0%EA%B9%8C-JWT-%ED%86%A0%ED%81%B0)
- [https://tjdrnr05571.tistory.com/3?category=876333](https://tjdrnr05571.tistory.com/3?category=876333)
- [https://co-de.tistory.com/28](https://co-de.tistory.com/28)
- [https://hyuntaeknote.tistory.com/6](https://hyuntaeknote.tistory.com/6)
