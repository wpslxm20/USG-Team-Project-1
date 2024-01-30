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


웹에서 multipart file로 받아야하고
받은 파일에 연결된 입력 스트림을 이용해서 BLOB로 저장

원본의 image type이랑 이름도 저장하는 컬럼이 필요하다.

```
Location 등록 시 필요한 데이터
reg_id
reg_name
reg_comment
reg_phone
reg_addr
reg_img
owner_id -> join Member

```



``` java
public class WebMvcConfig implements WebMvcConfigurer {
    
    String uploadPath = "file:///C:/location/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("images/**")
                .addResourceLocations(uploadPath);
    }
}

/*
이 코드는 Spring Framework에서 사용되는 WebMvcConfigurer 인터페이스를 구현하는 WebMvcConfig 클래스를 정의하고 있습니다. 이 클래스는 웹 애플리케이션의 MVC(모델-뷰-컨트롤러) 구성을 설정하는 데 사용됩니다.

여기에서는 파일 업로드 경로를 설정하고, 이를 웹 애플리케이션에서 접근할 수 있도록 리소스 핸들러를 등록하는 메서드를 제공하고 있습니다. 구체적으로는 addResourceHandlers 메서드를 재정의하여 정적 리소스의 핸들링을 설정하고 있습니다.

코드에서 uploadPath 변수는 파일 업로드 경로를 나타내며, "file:///C:/location/"로 초기화되어 있습니다. 따라서 이 경로에 저장된 파일은 웹 애플리케이션에서 "images/**"로 접근할 수 있게 됩니다. 즉, images라는 경로로 들어오는 요청은 실제로 서버의 파일 시스템에서 해당 경로로 매핑됩니다.

예를 들어, http://localhost:8080/images/example.jpg와 같은 요청이 들어오면 해당 요청은 실제로 파일 시스템의 C:/location/example.jpg 파일과 매핑됩니다.

이 코드를 사용하기 위해서는 해당 클래스가 Spring의 구성 클래스로 인식되도록 어노테이션을 추가하거나, XML 설정 파일에서 등록해야 합니다. 예를 들어, @Configuration 어노테이션을 추가하여 스프링 컨텍스트에 이 클래스가 구성으로 사용됨을 알릴 수 있습니다.
*/
```

``` java
@Getter
@Builder
@AllArgsConstructor
public class RegistImgReqDTO {

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;

    private static ModelMapper modelMapper = new ModelMapper();

    public static RegistInfoReqDTO of(LocationImg locationImg) {
        return modelMapper.map(locationImg, RegistInfoReqDTO.class);
    }
}

/*
정적 필드 및 메서드:
modelMapper: ModelMapper 라이브러리를 사용하여 객체 간에 매핑을 수행하기 위한 정적 필드입니다.
of 메서드: 주어진 LocationImg 객체를 RegistInfoReqDTO 객체로 변환하여 반환하는 정적 메서드입니다. 이는 modelMapper를 사용하여 객체 간 매핑을 수행합니다.
*/
```