# <img src="https://media.vlpt.us/images/jbb9229/post/226b55e4-efa7-4601-9c1f-580ca8e46a63/1100px_Redis_Logo_01.png">Redis

**In-Memory Data Structure Store**

Open Source (BSD 3 License)

## 특징

1. Remote에 위치해있다.

2. 프로세스에 존재한다

3. In-Memory 기반

4. **Key-Value** 구조 데이터 관리 시스템

5. Collection을 제공한다~~(이에 비교되는 Memcached는 제공안하나..?)~~

   외부의 Collection을 잘 이용하는것으로, 개발 시간을 단축하고 문제를 줄일 수 있다.

6. 자료구조가 Atomic하다 (즉, race condition을 피할수 있다. ~~하지만 잘못짜면 발생한다.~~)

7. `single-threaded` 특성이다.

## 자료 구조

- String
- Set
- Sorted Set
- Hash
- List

서비스 특성이나 상황에 따라 Cache로 사용할 수 있고, 
Persistence Data Storage로 사용할 수 있다.

## 왜 사용해야해??

서비스 사용자가 증가했을 때, 모든 유저 요청을 DB접근으로만 처리한다면,

DB 부하가 쏠리며 기존 성능을 기대하기 힘들다.

나중에 요청된 Request의 결과들을 미리 저장해두었다가 빨리제공하기위해 사용
수용량 자체는 적지만, 접근속도는 빠르다.

**결론 : 사용량 많아지면 DB는 제성능 못내니, Cache용 DB를 사용해 성능 향상 기대**

## 주의점

1. 메모리 관리를 잘하자
   
   레디스는 메모리 할당이나 해제에 메모리풀을 사용하지 않는다.
   
   `Memory Allocate`의 구현에 따라서 레디스 성능이 바뀐다.

   1-1. 메모리 파편화

      `Max Memory`를 설정하였더라도, 더 사용할 수 있으므로 관리가 필요하다
   
      `jemalloc`은 메모리를 페이지 단위로 반환하기 때문에 파편화가 일어난다.
   
      4.*대 버전 부터 완화시키고자 `jemalloc`에 힌트를 주는 기능이 들어갔으나, 확신을 주는 기능이 아니기때문에 주의를 항상 해야한다.
   
      - 다양한 사이즈를 가지는 데이터보다는 유사한 크기의 데이터를 가지는 경우가 유리하다
      - 큰 메모리를 사용하는 instance하나보다는 적은 메모리를 사용하는 instace여러개가 안전하다
   
2. O(n) 관련 명령은 주의하자.

   `Radis`는 `Single-Threaded`기반이다

   이는 처리시간이 긴 명령어를 중간에 넣으면 뒤따라오는 명령어들은
   그만큼의 대기시간이 추가된다

   ~~하지만 Get/Set명령어 같은경우 초당 10만개까지도 처리가 가능할정도로 빠르다~~

3. 





