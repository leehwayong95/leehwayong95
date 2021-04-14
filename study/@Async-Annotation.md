# :innocent:

# Spring Boot : @Async Annotation

기존에는 어노테이션을 그냥 써야하겠거니 하고 사용하고있었습니다.

이번 인턴십을 통해 볼 수 있었던 코드들을 보면서 
Spring 어노테이션에 대해 공부를 해야겠다고 생각했습니다.

Spring에는 다양한 어노테이션이 존재하는데요.
기능 구현에 중요한 어노테이션이 많아 추가적으로 학습을 진행하였습니다.

## @Async

병렬 프로그래밍을 위해 만들어진 어노테이션.

먼저 Async를 사용하기 위해선 **@EnableAsync**를 선언해야한다.
스프링의 Async는 **AOP**에 의해 동작한다.

> #### [AOP : Aspect Oriented Programming](AOP.md)
>
> 어떤 로직을 기준으로 핵심적인 관점, 부가적인 관점으로 나누어 보고
>
> 그 관점을 기준으로 각각 모듈화 하는것

@Async 어노테이션이 선언된 메서드는 **비동기 메서드로 동작한다**

비동기 메서드로 선언된 메서드는 최종적으로
springframework.aop.interceptor 패키지에 도달하며 'doSubmit' 메서드에 도달해
비동기로 동작하게되는데, Async로 선언된 해당 메서드의 리턴타입에 따라서 상이하게 구현 되어있다.

* CompletableFuture
* ListenableFuture
* Future
* No Return

> ##### Future
>
> 자바 1.5에서 등장한 비동기 계산의 결과를 나타내는 Interface.
>미래 시점에 결과를 얻을 수 있는 인터페이스로 사용된다.
> 
>비동기 계산을 위함으로, 호출을하고 다른작업을 하고 있다가, 
> 필요시 Future의 get메서드로 결과를 가져올 수 있다.
>
> 하지만 완료되지 않았을 때 get메서드를 실행하게 되면 결국은 블로킹이 일어난다.

> ##### CompletableFuture
>
> 메인 쓰레드에서 분리된 쓰레드에서 동작하며, 처리 완료 후
>성공 혹은 실패를 메인쓰레드에 알려주는 non-blocking 코드를 의미
> 
>기존 Future인터페이스 자체가 computation들을 중첩시키거나
> 에러를 처리하는 방법을 제공하지 않아, 여러 computation을 처리하기 위해서 
>CompletaionStage를 추가하여 이를 구현한 CompletableFuture는 여러가지 손쉬운 기능들을
> Java1.8에서 제공하였습니다.
>
> 결국 Future 인터페이스의 보완작입니다.

 * ### void Return Value

   비동기로 처리 해야하는 메서드가 처리 결과를 전달할 필요가 없는경우,
   Aysnc 어노테이션을 지정한 메서드에 'void'를 달면 된다.

   비동기 메서드를 실행하게되면 처음에는 main 쓰레드로 실행되다
   task-1이라는  별도의 쓰레드로 실행시킨다.
   
   리턴타입이 void인 경우엔 executor.submit(task)를 실행하고 곧바로 return null이 실행된다.
   
   > task-1은 스프링부트에서 자동으로 만들어 제공하는 ThreadPoolTaskExecutor에 의해 동작한다.

 - ### Future Return

   비동기 실행 후, 리턴값을 받아 처리해야한다면, Future를 사용해야한다.
Future 계열(Future, Listenable Future, CompletableFuture)중 가장 기본이 되는 Future이다.

   스프링에서 제공하는 AsyncResult는 Future의 구현체이며,
@Async 어노테이션에 AsyncResult를 하용해서 Future를 리턴할 수 있다.

   이 또한 위에서 말했다 싶이, get메서드를 사용하게되면 결과가 조회가(완료가) 될 때까지
Blocking 현상이 발생한다.

   즉, 논블로킹으로 동작하게 하려면 콜백 메서드를 처리하는 로직을 구현하면 된다.

 - ### ListenableFuture Return

   Future보다 조금더 고급화된 Future이다. 

   스프링에서 제공하는 AsyncResult는 Future의 구현체이면서 동시에 ListenableFuture의 구현체이다.
   그러므로 AsyncResult로 리턴을 구현할 수 있다.(그대로 리턴할때 AsyncResult를 생성해 리턴하면 된다.)

   ```java
   ListenableFuture<Integer> future = coffeeServicmagetPriceAsyncWithListenableFuture("latte");
   future.addCallback(s -> log.info("latte's price is  :" + s), e -> log.info(e.getMesseage()));
   log.info("non blocking")
   ```

   future.addCalback 메서드는 비동기 메서드의 내부 로직이 완료되면 수행이 되는 콜백기능이다.
   Future를 사용했을 때, 콜백 메서드를 사용한다면, 결과를 얻을때까지 무작정 기다릴 필요가 없다.

 - ### CompletableFuture Return

   AsyncResult에서 제공하는 completable 메서드를 사용하면 CompletableFuture로 리턴할 수 있다.

   위와 마찬가지로 Future를 상속하고있으며 get메서드 역시 사용할 수 있다.
   
   그러므로 get메서드를 사용하면 **일시적으로** 논블로킹처럼 보이게 되나,
   결국은 블로킹으로 처리되는 것을 알 수 있다.
   전체 과정을 non-blocking 으로 동작하게 하고 싶다면, 콜백함수를 사용해야한다.
   
   이때는 위와 다르게 CompletableFuture에서 제공하는 thenAccept메서드를 사용할 수 있다.
   
   (참고 : thenAccept말고 thenapply등 많은 메서드가 있다.)
   
   ```java
   CompletableFuture<Integer> future = coffeeService.get~~~("latte");
   log.info("non blocking 1 : ...");
   future.thenAccept(p -> log.info("latte's price: " + p));
   log.info("non blocking 2 : ...");
   ```
   
   메인스레드는 3번째 줄에 기재된 라떼의 가격이 도착하기 전에 다음 작업 (non blocking 1,2)를 수행하고,
   늦게 결과가 나온 latte의 가격을 표시하게된다.
   
   

