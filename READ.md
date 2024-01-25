# 로그인 회원가입 기능
DB 는 resource에 있는 datasource 자기자신에 맞는 DB정보로 변경하고 사용하면 됨.

demo(back) 실행 방법	

    cd demo
    mvn spring-boot:run
백 실행 잘안되면 종속성 문제기 때문에 mvn install이랑 DB 연결 잘 확인하길.

front 실행 방법	(react 사용함)

    cd front
    npm install
    npm start
이것도 마찬가지고 종속성 문제로 실행안될수 있으니 확인 잘하길





## 추가한 내용

- front
@mui/material @emotion/react @emotion/styled

- back
datasource:
    url: jdbc:mysql://localhost:3307/loc
    username: loc
    password: 1234


## 전체적인 로직 이해
### 홈페이지
- 업주
```
Home | 등록하기 | 조회하기 | 마이페이지 | 로그아웃
```

```
1. 등록하기 클릭 -> 지역정보 등록 페이지 (기본적으로 토큰에 로그인한 회원 정보가 들어가 있을 것)
-> 만약에 회원이 인증,인가된 회원이라면 (프론트에서 처리 -> 패스로 접속 가능하게 설정하고)
-> 회원정보는 owner_id, 나머지 등록시 필요한 정보를 보내서 만약에 owner_id가 맞다면, 
```
