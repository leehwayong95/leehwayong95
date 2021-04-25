<img src="https://blog.kakaocdn.net/dn/cVaw4d/btqDURwZDoX/q6XGmMMrktN33iW1v3gtMk/img.png" style="height: 200px">

# Go

- 특성
  - 전통적인 컴파일, 링크 모델을 따르는 범용 프로그래밍언어
  - Go는 일차적으로 시스템 프로그래밍을 위해 개발되었음
  - C++, Java, Python의 장점을 뽑아 만들었다.
  - 컴파일러를 통해 컴파일 됨

- Editor

  필자는 VSC를 이용하여 연습했다. 하지만 다른 방법도 많으므로 찾아보길바란다.

- 실행 방법

  - Step 0; Go 컴파일러를 설치한다.

    - Windows : [여기](http://golang.org/dl)에서 *.msi 파일을 받아 설치한다.
    - Linux : [참조](https://golang.org/doc/install?download=go1.16.3.linux-amd64.tar.gz)
    - MAC : [여기](http://golang.org/dl)에서 *.pkg 파일을 받아 설치
    - [공통 참조](http://golang.site/go/article/2-Go-%EC%84%A4%EC%B9%98%EC%99%80-Go-%ED%8E%B8%EC%A7%91%EA%B8%B0-%EC%86%8C%EA%B0%9C)

  - Step 1; 메모장에 적는다

    ``` Go
    package main
    
    func main() {
        println("Hi there!");
    }
    ```

  - Step 2; Hithere.go 로 저장한다

  - Step 3; 설치가 재대로 됐다면 명령프롬프트(cmd) 에서 `go run (저장한경로)/Hithere.go`를 실행한다

  - Done! :D

  이러한 단계를 메모장켰다, cmd 켰다 귀찮기 때문에
  나는 Editor를 사용하여 작성할 것이다.

## 기본 문법

- 변수

  Go는 js와 비슷한 것 같다.

  변수를 선언할 때는 (사실 안써도된다. 그이유는 밑에 설명) var를 사용하여 선언한다.

  특이한점은 변수의 형식을 이름 뒤에 선언한다.

  ``` go
  var A string = "Hello"
  A := "Hello"
  ```

  또한 특이한 점으로 `:=`을 들수 있다.

  두번째 줄에 적혀진 `A`변수 선언은 위 명령어와 같은 의미로서 초기화와 선언을 동시에 한다는 축약어다.

  그래서 사실상 var를 잘 안쓰게 된다.

- 상수

  const 로 상수를 선언한다.

  숫자 상수는 명시적 Casting, 함수호출, 변수 설정까지는 형식이 주어지지 않습니다.

  또한 var와 같이 사용도 가능하다.

  ``` go
  const s string = "Hello"
  const n = 50000
  const a = 3e32
  ```

- for

  Go에서는 유일한 반복문이다.

  ``` go
  for /*조건문*/ {
  	반복할 내용
  }
  ```

  다른 언어와 다르게 초기값, 반복문, 증감식을 감싸는 괄호가 따로 없다.

  하지만, 중괄호는 for문의 필수 조건입니다.

  또한, 초기값, 반복문, 증감식은 모두 생략될 수 있습니다.
  모두 생략한 경우엔 `while(true)`와 같이 사용됩니다

- if

  for와 비슷하게 조건절에 괄호가 없다.

  하지만 중괄호는 필수다.

  또한, 초기값을 if문 앞에서 줄 수 있으며, `;`으로 조건절과 구분한다

  ``` go 
  if a := 1 ; a>2 {
      fmt.Println("a is bigger then 2")
  }
  ```

  참고사항1. **3항 연산자**가 Go lang에는 없다.

  참고사항2. **단항연산자**는 정말로 혼자 써야한다.

  ``` go 
  a := 1
  fmt.println(a++) //compile Error
  
  ++a // compile Error
  b := a++ // compile Error
  
  a++
  fmt.Println(a) // Working!
  ```
  
  


# :innocent:이 문서는 현재 작성 중입니다. 

