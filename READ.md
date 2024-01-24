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
