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

  

  # :innocent:이 문서는 현재 작성 중입니다. 

