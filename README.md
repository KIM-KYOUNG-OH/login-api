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
  - @ResponseBody나 Mybatis 동작시 Getter가 필수적이므로 lombok이 적용안되면 getter를 명시해줘야함
  - https://insanelysimple.tistory.com/106
- mapper xml 파일 경로 못찾는 문제
  - mapper 파일에 확장자(.xml) 붙여서 해결
- snake case인 table과 camel case인 class 매핑 안되는 문제
  - mapUnderscoreToCamelCase 옵션 true로 설정해서 해결
