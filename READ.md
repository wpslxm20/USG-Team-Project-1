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

이제 등록된 매장이 존재할 것이므로
2. 메인페이지에서 화면 띄어주기
-- 여기서 프론트 작업까지 같이 해주자

-> DB에서 loc 정보를 받아와서 (id 값을 통해 일치하는 매장 정보)
-> 그 중 필요한 정보인 id, name, comment, (평점은 Review DB가 설정되고 나서 가능 4로 default설정)
-> 이미지 파일은 outputStream을 통해 가져오기

-> 생각해보니 홈페이지는 테이블 형태로 있을 텐데,
-> 일단 보여줄 페이지의 순서가 중요한데
-> 먼저 데이터를 전송하는 법 부터 설정한 후, (구성중)

-> 페이지를 보여줄 순서를 프론트에서 백으로 전송하고
-> 그 순서에 맞게 DB에서 페이징 처리해서 보여주는 것이 맞을 듯 (다음에 할 일)

```
