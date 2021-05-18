<img src="https://lh3.googleusercontent.com/proxy/XcfBsYcq_uoWfC8Gkzht2U2bWQqccZ2465tUoGK96Gc9Dzf2Qq9IwhShaXRn3LRldKjRbSnZxYGY2GAmGaRMcTg">

# JPA

이름처럼 `JAVA`에서 제공하는 ORM기술을 이용한 API이다.

자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스

ORM이기 때문에 자바 클래스와 DB테이블을 매핑한다.

내가 맡은 프로젝트에선 mybastis를 이용하는 것이 아닌, JPA를 이용하여 [`Domain Driven Design`](DDD.md)를 구현하는 것이 목적이다.

## 이걸 왜 써야해??

GoLang을 이용하여 push서버를 구현했을 때에도 `Gorm`이라는 ORM 모듈을 이용했는데, 아직도 이해가 가지 않아 이 문서를 쓴다.

장점은 3가지라고 생각한다.

1. 구현체 교체의 용이성
2. 저장소 교체의 용이성.
3. SQL 중심적인 개발에서, 객체중심 개발로
4. Object와 RDB의 패러다임 불일치 해결로 인한 생산성 증가

이제 조금 이해가 가는 부분이다. ~~사실 구현체 교체는 이해 못했지만~~

사실 프로젝트를 진행하면서 나는 단 한번도 `Table`을 만든 적이 없다.
Local에서 테스트하고, Dev서버에 올릴 때도, 알아서 `Table`을 만들 필요가 없다.

왜냐하면 알아서 `JPA`가 Class를 매핑 하기 위해 테이블을 수정 및 새로 만들기 때문이다.

또, DB인출을 위해 만들어 두었던 Class들이 실질적으로 DB CRUD를 진행 했을 때, SQL을 이용하여 한번 더 Insert Query를 작성해야 한다는 것..

두 번의 작업이 필요하기 때문에 이를 줄이고자 쓰는 것 같다.

~~하지만 이런 편함 때문에 내가 왜 암에 걸려야 하는.....~~

## 동작과정

JPA는 어플리케이션, JDBC 사이에서 동작한다.

많은 초보 개발자들은 JDBC를 이용해서 직접 쿼리를 진행하는데 대신 JPA라는 친구가 쿼리를 해주는 것이라 생각하면 편할 것 같다.

개발자가 JPA를 사용하면, JPA 내부에서 JDBC API를 사용하여 SQL을 호출하고, DB와 통신한다.

## Spring Data JPA

나는 Spring boot 에서 `gradle Project`를 만들어  적용하고 있다.

Hystrix (Spring cloud) 때문에 Spring boot 버전은 내려서 `2.4.*`를 사용한다.

``` gradle
dependencies {
    compile('org.projectlombok:lombok')
    compile('org.springframework.boot:spring-boot-stater-data-jpa')
...
```

~~여러가지가 있지만, `spring-boot-starter-web` 등 당연한 것은 생략했다.~~

현재 실제 적용한 것과 달라 원인 분석 후 작성 예정 :innocent:

## 겪은 문제점

- 양방향 Entity 인출 후 Entity 반환 시, 재귀 호출로 인한 무한 루프 호출..

  첫번째 적용 방법은 `@JsonIgnore`어노테이션을 이용하여 Json에 포함되지 않게 하는 것 이었으나, 이는 양방향 Entity를 무시하는 것이다. 

  또한 DTO로 변환 시켜 반환하는 것도 방법이었으나
  개인적으로 JPA를 쓰는 메리트가 사라지는 느낌이었다.

  - 원인

    JPA의 직렬화 안에서, 무한으로 참조되는 양방향 객체들의 무한 참조가 원인.

  - 해결방안

    ``` java
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "personchargeId")
    public class Personcharge {
        @Id
        @Column(nullable = false, length = 45)
        ...
    }
    ```

    양방향 관계에 있는 `Entity Class` 모두 `@JsonIdentityInfo` 어노테이션을 달아 한번 직렬화 된 객체는 id값으로만 직렬화가 되는 것을 확인했다.

    참고한 예제에서는 generator를 int, String으로 했지만, 필요 없는 `@id`등 키 값이 딸려 나와 `PropertyGenerator`로 바꾸어 지정했다.

    - 추가 조사

      `generator`를 `PropertyGenerator`로 지정하니, 해당 하위 값의 `@id`가 표시만 되고 참조를 하지 않지만, 표기 되는 것을 막고 싶다.

    

