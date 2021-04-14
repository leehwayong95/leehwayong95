# :innocent:

# Spring Boot Security

기본적인 세션관리, HTTP 보안, 어플리케이션 보안을 담당하고있다.

## 순서

1.  Http Request 가 들어온다
2. AuthenticationFilter가 검사를 한다(Spring Security가 한번더 검사해준다는 뜻)
3. Filter가 유요하다 판단되면 인증용 토큰(객체)를 생성한다.
4. AuthenticationManager가 실제로 인증을 진행할 AuthenticationProvider 에게 전달한다.

이 부분은 Spring 이 알아서 하는 부분이고 이후부터는 개발자가 직접구현해야한다.

***

## dependency 추가

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

프로젝트에 `dependency` 추가

##  Security Config 클래스 등록

``` Java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure (AuthenticationManagerBuilder auth) {
        //실제 인증 진행할 Provider
    }
    
    @Override
    public void configure (WebSecurity web) {
        //Image, JavaScript, CSS directory Security setting
    }
    
    @Override
    protected void configure (HttpSecurity http) throws Exception {
        //Setting security for HTTP **Important**
    }
}
```

시큐리티를 설정할 클래스에 **@Configuration** 와 **@EnableWebSecurity** 어노테이션을 달아준다.

여기서부터는 개발자마다 구현하는 방법이다르다.
정답이 없으니.. 다른 글들을 참고하며 만들면 좋다.

### Override Method 설명

- AuthenticationManagerBuilder를 매개변수로 받는 configure method

  여기서 DB로부터 아이디, 비밀번호가 맞는지, 해당유저가 어떤 권한을 갖는지 체크한다.

  **이부분은 위설명의 연장으로 5번에 해당한다**

  UserDetailsService 인터페이스를 상속받은 클래스가 있다면, 그 클래스에서 인증을 시도하면 된다.

- Websecurity를 매개변수로 받는 configure method

  이미지, css, JavaScript파일을 접근 가능하게 처리하는 소스를 입력한다.