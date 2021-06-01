<img src="https://repository-images.githubusercontent.com/36793280/5eec9480-8c3c-11e9-8069-5fc3e01c739a">

# Resilience4j

Netflix Hystrix가 추가 개발되지않고, 유지보수만 하겠다는 발표와함께 해당 모듈을 사용하기 권하고함으로서

이 모듈에 대해 조금 학습을 한다.



## 간단 실행

### 의존성 추가

``` bundle.gradle
dependencies {
	//Resilience4J
	compile("io.github.resilience4j:resilience4j-spring-boot2:1.3.0")
	compile("io.github.resilience4j:resilience4j-reactor:1.3.0")
	compile("io.github.resilience4j:resilience4j-timelimiter:1.3.0")
	...
}
```

### 설정 추가 (yml)

``` yaml
resilience4j:
	circuitbreaker: 
		backends: 
        	[설정이름]: 
                ringBufferSizeInClosedState: 30
                ringBufferSizeInHalfOpenState: 30
                waitDurationInOpenState: 5000ms
                failureRateThreshold: 20
                registerHealthIndicator: false

```

- 옵션 설명
  - ringBufferSizeInClosedState: Circuit이 닫혀있을 때(정상일때) Ring Buffer사이즈. (Default 100)
  - ringBufferSizedHalfOpenState: half-Open(비정상에서 정상으로 돌아오기 직전)상태일 때 RingBuffer사이즈 (Default 10)
  - waitDurationInOpenState: half closed전에 circuitBreaker가 open 되기 전에 기다리는 기간
  - failureRateThreshold: Circuit 열지 말지 결정하는 실패 threshold 퍼센트(실패 비율. 이 이상으로 올라가면 Open)

### 설정 등록

``` java
@Configuration
public class WebdulConfig {
    private static final String CIRCUIT_NAME = "[설정파일에서 설정한 이름]";
    
    @Bean
    public io.github.resilience4j.circuitbreaker.CircuitBreaker circuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(CIRCUIT_NAME);
    }
}
```

이렇게 설정파일에서 설정한 이름으로 진행하게되면 자동 설정이 되어 Override된다.

## Core modules 설명

1. CircuitBreaker : CircuitBreaker 패턴을 구현한 모듈. 호출의 결과를 저장하고 집계하기위해 슬라이딩 윈도우를 사용한다.

   - count-based window

     마지막 N개 요청의 결과를 집계한다.

   - time-based window

     마지막 N초 동안 요청의 결과를 집계한다.

   이 두가지 윈도우중 하나를 선택하여 사용할 수 있다.

   요청 실패율이 몇퍼센트 이상일 때 Open를 할지, 지연된 응답이 몇퍼센트 이상일 때 Open할지 설정할 수 있다.

   Open이 되면 CallNotPermiitedException과 함께 요청을 거절하게된다.

2. Bulkhead

3. RateLimiter

4. Retry

5. TimeLimiter

6. Cache