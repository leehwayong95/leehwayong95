<img src='https://ichi.pro/assets/images/max/640/0*gRZh5dHFWz-nFu7Z.png'>

# Netflix Hystrix

MSA 분산 시스템에서 **장애**는 피알수 없는존재다.

Netflix에서 ***Circuit Breaker Pattern***을 구현한 라이브러리. MSA에서 장애 전파를 방지 할 수 있다.

## Circuit Breaker Pattern

<img width="300" src="https://martinfowler.com/bliki/images/circuitBreaker/sketch.png">

> *출처 : https://martinfowler.com/bliki/images/circuitBreaker*

예를들어, 하나의 `worker`(서버)가 있고, 이 서버가 작업하기 위해선 다른 `상위서버`에게 Request를 요청해할때,

상위서버가 뻗을때를 가정하자. `worker`의 일은 밀려있고, 개발자가 최대한 넉넉하게 잡아둔 `TimeOut`시간을 여러번 보내게되면

자연스레 `worker`의 일은 더더욱 밀리게되고, 이는 다른 하위 서버에게 까지 영향을 미쳐 **장애 전파 효과**를 보게된다.



Netflix Hystrix는 이러한 **장애 전파 방지**를 위해서, 지정한 오류율을 넘기면, 그 이후로 해당 경로(Circuit)는 차단하는 

CircuitBreaker로 Circuit을 Open(회로 개방)한다.



## Hystrix Flow

1. `HystrixCommand`, `HystrixObservableCommnad` 객체 생성
2. Commnad 실행
3. 캐시 여부 확인
4. Circuit Status 확인
5. 사용가능한 Thread Pool, Queue, Semaphore가 있는지 확인
6. `HystrixObservableCommand.construc()` 혹은 `HystrixCommand.run()` 실행
7. 회로 상태 계산Calculate Circuit health
8. fallback 실행
9. 응답 반환

## 구현

1. Circuit Health Check를 위한 최소한의 요청이 있을 때 (`HystrixCommandProperties.circuitBreakerRequestVolumeThreshold()`)
2. 그리고, 지정한 오류율을 초과했을 때 (`HystrixCommandProperties.circuitBreakerErrorThresholdPercentage()`)

3. 회로의 상태를 `CLOSED`에서 `OPEN`으로 변경

4. 회로가 열린 동안, 모든 요청에 대해서 fallback method를 **바로**실행

5. 일정 시간이 지난 후(`HystrixCommandProperties.circuitBreakerSleepWindowInMilliseconds()`), 하나의 요청을 원래 method로 실행(`Half OPEN`).

   이 요청이 실패한다면 `OPEN`으로 두고, 이요청이 성공한다면 `CLOSED`로 상태 변경. 다시 1번으로 돌아가는 루틴

## 기본값

- metrics.rollingStats.timeInMilliseconds : 오류 감시 시간, 기본값 10초
- circuitBreaker.requestVolumeThreshold : 감시 시간 내 요청 수, 기본값 20초
- circuitBreaker.errorThresholdPercentage : 요청 대비 오류율, 기본값 50



### 겪은 문제점들

- 어노테이션(@EnableCircuitBreaker, @EnableHystrix 등)이 뜨지않는다.

  아무리 의존성 주입을 하고, Sync gradle도 누르고.. 해도 뜨지않는다.

  ``` Gradle
  dependencies {
  	implementation 'org.springframework.cloud:spring-cloud-starter-netflix-hystrix'
  }
  ```

  - 해결

    ```gradle
    dependencies {
    	//hystrix
        implementation 'org.springframework.cloud:spring-cloud-starter-netflix-hystrix:2.1.0.RELEASE'
    }
    ```

    버전지정을 해주니 되었다...  ~~이게 최선이 아닐텐데... 라고 잠시생각하며~~ 빠른개발 진행을 한다.

- Error creating bean with name 'configurationPropertiesBeans' defined in class path resource

  위 문제를 해결하고, 서버를 준비하여 application run을 하였으나, 에러가 뜬다.

  Bean name을 못 찾는것같다. StackOverFlow에서도 버전문제다.. 라고해서 
  Spring boot의 버전을 2.4.5를 사용중이었다가 2.4.0으로 내렸으나, 달라지는건 없었다.

  - 해결

    ``` gradle
    plugins {
        id 'org.springframework.boot' version '2.3.8.RELEASE'
        id 'io.spring.dependency-management' version '1.0.11.RELEASE'
        id 'java'
    }
    ```

    Spring Boot 2.4.* 버전을

    Spring Cloud에서 지원하지 않아 생기는 문제였다.

- Spring Boot 다운그레이드 이후 java.sql.SQLNonTransientConnectionException 발생

  위 문제 (Spring boot, Spring Cloud 버전 매칭)을 해결하고 나니 바로 DB Connection이 되지않는 문제가 발생했다.

  나는 profile을 적용한 상태여서 Local로 돌아가게끔 설정을 해놓았다. 아래 dev, prod profile이 존재했다.

  prod 부분이 문제였는데, 계속 http://DB:32306/Test로 JPA가 접속시도를 한다는것이다.

  prod 부분은 K8s부분의 DNS이용하는 부분이라 연결이 안되는게 당연한데,, 왜 계속 연결하려는지 잘 모르겠어서 우선 주석처리를 하고

  개발을 먼저진행했다.

  - 해결

    - 수정 전

    ``` gradle
    ---
    spring:
      config:
        activate:
          on-profile: prod
          ...
    ```

    - 수정 후

    ``` gradle
    ---
    
    spring:
      profiles: prod
    ```

    Spring boot 2.4.*이후로 부터 Profiles 설정 방법이 달라졌다.

    나는 Spring boot 2.3.8로 Downgrade를 진행했기 때문에, profiles의 이름을 인식하지 못하여, 문서의 마지막 DB정보를 가져오게 되었다.

- 설정 파일의 `timeoutInMilliseconds`가 반영이 되지 않음.

  `RestTemplate`을 이용한 Request시, `timeoutInMilliseconds`을 10000ms (10s)로 설정해두고 응답을 기다렸을때, 2000ms 이내로 응답이 fallback했다.

  어노테이션을 달아 `timeoutInMilliseconds`을 지정해도, 마찬가지 문제가 발생했다.

  - 원인

    1. `RestTemplate`의 기본 Timeout 시간이 2s 으로 지정되어있거나,
    2. Spring Profiles 적용으로 인한 application.yml의 depth가 `spring:`  하위 depth로 지정해놓아서 인식못함.

    둘 중 하나였다.

  - 해결방안

    1. `ResTemplate` 제거

       우선 `RestTemplate`을 사용하지 않았다. 대신 `WebClient`를 사용하여 객체를 생성했고, 이를 이용하여 `Exchage()`했다
       처음에는 TimeOut되지않고 메서드에 직접 등록한 500000ms이내로 들어와 잘 지정되었다.

       하지만 메서드에 직접 등록된 `TimeoutInMilliseconds`를 제거하니, 바로 기본값으로 돌아왔다.

       `application.yml`에 등록된 숫자로 500000ms 를 넣어주니, 계속 2000ms 정도만 지나면 `fallback`메서드로 떨궜다.

    2. 해결중....

    