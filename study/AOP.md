# :innocent:

# Spring AOP (Aspect Oriented Programming)

**AOP**는 **관점 지향 프로그래밍**이라 불린다.

어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어서 보고,
그 관점을 기준으로 각각 모듈화 하겠다는 뜻.

각각의 Class에 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 비즈니스 로직에서
분리하여 재사용하겠다는것이 AOP의 취지이다.

## AOP 의 주요 개념

- **Aspect** :위에서 설명한 흩어진 관심사를 모듈화 한 것.
- **Target** : **Aspect**를 적용하는 곳 (Class, Method.. 등등)
- **Advice** : 실질적으로 해야할 일에 대한 것, 실질적인 부가기능을 담은 구현체
- **JointPoint** : **Advice**가 적용될 위치로 끼어들 수 있는 지점을 뜻한다. 
  (ex : 메서드 진입지점, 생성자 호출지점...)
- **PointCut** : **JointPoint**의 상세항 스펙을 정의한 것. 구체적으로 **Advice**가 실행될 지점을 정할 수 있음.

## 스프링 AOP 특징

- 프록시 기반의 AOP 구현체
  (프록시를 쓰는 이유는 접근제어 부가기능을 추가하기 위해)
- 스프링 빈에만 AOP 적용 가능
- 모든 AOP기능을 제공하는게 아닌, 스프링 **IoC**와 연동하여 엔터프라이즈 애플리케이션에서 가장흔한 문제
  (중복코드 등등)에 대한 해결책을 지원하는 것이 목적

## 적용 방법

### Dependency 적용

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

### 클래스에 @EnableAspectJAutoProxy 적용하기

```java
@EnableAspectJAutoProxy
... class ...
```

### 공통기능을 정의하고 공통기능이 사용될 시점 정의

```java
@Aspect
@Component
public class ....
```









