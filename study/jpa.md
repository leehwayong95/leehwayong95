<img src="https://lh3.googleusercontent.com/proxy/XcfBsYcq_uoWfC8Gkzht2U2bWQqccZ2465tUoGK96Gc9Dzf2Qq9IwhShaXRn3LRldKjRbSnZxYGY2GAmGaRMcTg">

# JPA

이름처럼 `JAVA`에서 제공하는 ORM기술을 이용한 API이다.

자바 어플리케이션에서 관계형 데이터베이스를 사용하는 방식을 정의한 인터페이스

ORM이기 때문에 자바 클래스와 DB테이블을 매핑한다.

내가 맡은 프로젝트에선 mybastis를 이용하는 것이 아닌, JPA를 이용하여 [`Domain Driven Design`](DDD.md)를 구현하는 것이 목적이다.

## 이걸 왜 써야해??

GoLang을 이용하여 push서버를 구현했을 때에도 `Gorm`이라는 ORM 모듈을 이용했는데, 아직도 이해가 가지 않아 이 문서를 쓴다.

장점은 4 가지 라고 생각한다.

1. 구현체 교체의 용이성
2. 저장소 교체의 용이성.
3. SQL 중심적인 개발에서, 객체 중심 개발로
4. Object와 RDB의 패러다임 불일치 해결로 인한 생산성 증가

이제 조금 이해가 가는 부분이다. ~~사실 구현체 교체는 이해 못했지만~~

사실 프로젝트를 진행하면서 나는 단 한번도 `Table`을 만든 적이 없다.
Local에서 테스트하고, Dev서버에 올릴 때도, 알아서 `Table`을 만들 필요가 없다.

왜냐하면 알아서 `JPA`가 Class를 매핑 하기 위해 테이블을 수정 및 새로 만들기 때문이다.

또, DB인출을 위해 만들어 두었던 Class들이 실질적으로 DB CRUD를 진행 했을 때, SQL을 이용하여 한번 더 Insert Query를 작성해야 한다는 것..

두 번의 작업이 필요하기 때문에 이를 줄이고자 쓰는 것 같다.

~~하지만 이런 편함 때문에 내가 왜 암에 걸려야 하는.....~~

## 동작 과정

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

  첫 번째 적용 방법은 `@JsonIgnore`어노테이션을 이용하여 Json에 포함되지 않게 하는 것 이었으나, 이는 양방향 Entity를 무시하는 것이다. 

  또한 DTO로 변환 시켜 반환하는 것도 방법이었으나
  개인적으로 JPA를 쓰는 메리트가 사라지는 느낌이었다.

  - 원인

    JPA의 직렬화 안에서, 무한으로 참조되는 양방향 객체들의 무한 참조가 원인.

  - 해결 방안

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
      
      - 해결방안
      
        `@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "[@Id 속성 명]")`
      
        위 사항을 적용하여 `@id`값을 삭제하였다.
  
  - 참조 : [참조 페이지](https://thxwelchs.github.io/JPA%20%EC%96%91%EB%B0%A9%ED%96%A5%20Entity%20%EB%AC%B4%ED%95%9C%20%EC%9E%AC%EA%B7%80%20%EB%AC%B8%EC%A0%9C%20%ED%95%B4%EA%B2%B0/)
  
- Select 쿼리시 where 절 안에 있는 deletedt is null 적용 안되는 현상

  JPA를 이용해서 where절안에 `findApprovalByDeletedtIsNullAndPersonCharge (PersonCharge p)`를 실행했으나,
  `Deletedt`가 Null이 아닌 `Approval`객체들이 쿼리에 걸려 나오는 현상이 발생했다.

  Hibernate Debug 로 찍힌 SQL 쿼리를 직접 쿼리를 하면 재대로 `Deletedt`가 비어있는 것이 나온다.

  Join절의 `fetch = FetchType.EAGER`를 적용함에도 불구하고, 계속 나온다..

  - 해결 방안

    검색어를 `Soft Delete in JPA`로 바꿔 검색하니 하나 나오는 것이 있었다.

    Entity안에 `@SQLDelete` 와 `@Where`어노테이션을 추가 해주는 것이다.

    ``` java
    @Entity(name="approval")
    @Getter
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "approvalId")
    @SQLDelete(sql="UPDATE approval SET deletedt = now() WHERE approval_id = ?")
    @Where(clause = "deletedt IS NULL")
    public class Approval implements Serializable {
        @Id
        @Column(nullable = false)
        private String approvalId = UUID.randomUUID().toString();
    
        @OneToOne(cascade = CascadeType.ALL, optional = false)
        @JoinColumn(name="documents")
        private Documents documents;
    ...(중략)
    ```

    이렇게 하면, 해당 Entity를 삭제 시에도, Soft Delete 방안이 적용되어, Update Query 를 수행하고, 
    또한 해당 객체 검색 시엔 `@Where`어노테이션 안에 들어있는 해당 조건을 모두 추가 해주는 것이다.

    JPA는 편하면서도 불편한 존재다... 조금 더 공부를 많이 해야 할 친구

- com.fasterxml.jackson.databind.exc.InvalidDefinitionException: No serializer found for class org.hibernate.proxy.pojo.bytebuddy.ByteBuddyInterceptor and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS)

  위 문제 (Where 절에 IsNull 적용 안되는 문제)를 해결 하고 나서 상세 조회가 필요하여 조회하는 API를 뚫고,

  조회를 했다. 하지만 위와 같은 문제가 발생했다. 연속적인 조회 때문에 나는듯했다.

  - 원인 : `@JoinColumn`의 `@ManyToOne(.... fetch = FetchType.LAZY)`

    LAZY 방식은 필요한 데이터가 있을 때 마다 (해당 테이블을) 다시 쿼리를 진행하여 조회를 하는 방식이다.

    다시 말해서, Join을 사용하지 않는 쿼리다. 이 방식은 복잡한 Join절에서 예상치 못한 결과물을 볼 수 있기 때문에 사용한다.

    하지만 나는 간단한 프로젝트이기 때문에 다른 방식으로 진행한다

  - 해결 : `@ManyToOne(... fetch = FetchType.EAGER)`

    생각보다 너무 단순했지만, Join 을 이용하는 것을 사실상 다시 한번 생각해 봐야 할 듯하다.

  

