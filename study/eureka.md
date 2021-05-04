# Eureka

Eureka는 Middle-tier Server(어플리케이션 서버)의 로드밸런스와 시스템 대체 작동(FailOver)을 위해 서비스를 배치해주는 REST기반 서비스

AWS  Cloud에서 사용되고, 이를 `Eureka Server`라 부른다.

### Eureka Client

`Eureka Server`와 상호작용을 더 쉽게 해주는 것을 **`Eureka Client`** 라고 부른다.

클라이언트도 `Round-Robin`방식 로드밸런스를 내장하고있다.



### 왜 사용하나?

AWS Cloud에서는 특성상 서버가 자주 꺼지고, 켜지고한다.

IP주소와 hostname을 이용하는 기존 로드 밸런스와 다르게
AWS에서 로드 밸런스는 좀 더 정교한 능력을 요구한다.

IP 주소는 수시로 변하기 때문에 로드 밸런서가 서버를 등록하고 해지하는 작업을 유동적으로 할 수 있게 해야한다.

AWS에서는 middle tier 로드밸런스를 제공하지않기 때문에 Eureka에서는 이를 제공한다.



