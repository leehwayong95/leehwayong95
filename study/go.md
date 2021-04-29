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
  
- for (feat. range)

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

  또, Go에서는 `for..each`, `for..in` 같은게 없습니다.
  오직 단순히 하나. `for`문 만이 존재합니다.

  (C와...) 다른점이 하나 있다면 **`range`문**입니다.

  ```go
  func CombinString (str ...string) total string {
    total = ""
      for _/* = index*/,eachstring := range str {
          total += string
      }
      return //반환형에 변수명을 지정해 뒀다면, 꼭 반환형을 안적어도 된다.
  }
  ```

  이것은 `str`이라는 `string[]`변수를 받아 `for..each`문처럼 사용하는 반복문입니다.

  이렇게 `range`를 사용하면 for만 있어도 `js`처럼 편안한 for문을 쓸 수 있을것입니다.

[^range 사용 주의점]:반환이 `index`와 `value`로 반환이 되므로, index를 쓰지않는경우 `_`로 무시하여 반환받아야한다. `_`를 작성하지 않는다면, 문법오류를 낼것이다.

- Variable expression

  ``` go
  func canIDrink (age int) bool {
      if koreanAge := age + 2 ; koreanAge > 20 {
          return true
      }
      return false
  }
  ```

  go에서 사용하는 variable Expression입니다.

  우리가 평소 사용하는것과 같이 작성하자면

  ``` go
  ...
  	var koreanAge int = age + 2
  	if koreanAge > 20 {
  ...
  ```

  이렇게 작성하여, 함수 내에서 사용될것이라 예상되는 문법을 사용하지만

  go에서는 조금더 적극적이게 if 문안에서만 사용할 것이라고 표현하는 것이다.
  중요하지 않지만, 처음보는것 위주로 작성하다보니 이것도 적게되었다.

- switch

  기존 개발자라면 흔하게 보던 switch이다. go의 특이한것만 적겠습니다.

  - 표현식 사용가능

    ``` go
    func checklenID (id string) bool {
        switch {
            case len(id) < 3:
            	return false
            case len(id) > 22:
            	return false
    	    default:
            	return true;
        }
    }
    ```

    변수를 지정하지 않고, 표현식 사용이 가능하다.

    크고 작고를 통해 케이스에 부여하여, 맞는 것을 골라 리턴된다.

    또한, 일반적인 switch 처럼 변수를 지정할 수 있고, 위에 언급한
    Variable Expression 도 사용이 가능하다.

  - 변수 지정

    위에서도 말했듯이, 변수 지정을 할 필요가 없다. 이는 여러가지로 사용이 가능하게 열어둔 것 같다.

- 포인터 (Pointer)

  포인터는 C언어를 배우면서 흥미롭게 봤던 내용이다.

  ``` go
  func main () {
      str := "HiThere!"
      replace(&str)
      fmt.Println(str)	//result > HelloWorld!
  }
  
  func replace (str *string) {
      *str = "HelloWorld!"
  }
  ```

  분명 `replace`는 반환을 하지 않지만, 매개변수로 `str`의 메모리 주소를 받아,
  `*str = "HelloWorld!"`를 통해 main문에 있는 `str`에 접근하게된다.

  C를 안한지 오래되서 반가울지경이다.

- slice

  ``` go
  func main () {
      dogs := []string{"코카스파니엘","비글","슈나우저"}
      fmt.Println(dogs)
      // result
      // [코카스파니엘 비글 슈나우저]
  }
  ```
  
  배열이랑 비슷하다. ~~아니 완전 똑같아보인다 다른점을 모르겠다..~~
  
  차이점은 길이가 지정되고, 안되고의 차이다.
  슬라이스는 가변의 길이를 가질 수 있다. 물론 아래 명령어를 사용해야한다
  
  - append
  
    ``` go
    ...
    	dogs = append(dogs, "치와와")
    	fmt.Println(dogs)
    	//result
    	//[코카스파니엘 비글 슈나우저 치와와]
    ```
  
    슬라이스에 하나 더 추가하는 명령어다. 
    `append`만 사용한다면 에러를 낼것이다. 왜냐하면 `append` 자체로만은 `dogs`의 value를 변화시키지 못하기 때문이다.
  
    그래서 `dogs =...`로 반환을 받아 dogs값에 반환된 슬라이스를 넣는것이다.
  
    ~~신기방기~~
  
  - 잘라내기
  
    저러한 슬라이스들 잘라내고 싶을때도 있다. append 했다면 잘라내야지
  
    ```go
    	fmt.Println(dogs[1:]) 
    	//[비글 슈나우저 치와와]
    	fmt.Println(dogs[:2]) 
    	//[코카스파니엘 비글 슈나우저]
    	fmt.Println(dogs[:len(dogs)-1]) 
    	//[코카스파니엘 비글 슈나우저]
    ```
  
    `Slice[min:max]`로 사용하며,
     `min+1`번째 요소부터 `max`번째 까지 반환해준다.
  
    자주 쓸것같아 적는다.
  
- map

  우리가 항상 쓰던 HashMap과 같지만 조금 다르다고 한다. (니꼬왈)

  또, 형식이 다른 방법은 `구조체`를 통해 넣는다고한다. 아직 많이 배워햐함.

  ```
  func main () {
  	testing := map[string] int {"FD": 5058, "JJ": 1234}
  	for key, value := range testing {
  		fmt.Println(key, value)
  	}
  }
  ```

  `map`은 선언시에 대괄호안에 있는 것이 `key`, 그 밖에있는것이 `value`이다.

  `map [key] value` ; 이 폼에 따라 해당 하는 곳에 형식을 작성한다.

  위의 에저는 키가 `string`형식이고 값은 `int`형식이다.

  여러 추가 지원 함수가 있는데 그것은 나중에 배워보자.

- struct

  구조체다. 조금 다른 구조체다. 객체같으면서 object같으면서 구조체다.
  
  ``` go
  package main
  import "fmt"
  type computer struct {
      cpu string
      ram int
      hdd bool
      ssd bool
      storage int
  }
  
  func main () {
      // 아래와 같이 적을 수도있습니다만,
      // myCom := computer{"i5", 16, false, true, 256+512}
      // 이게 더 시각적으로 보기에 편합니다.
      myCom := computer{cpu: "i5", ram: 16, hdd: false, ssd: true, storage: 256+512}
      fmt.Println(myCom)
      // result > {i5 16 false true 768} //
  }
  ```
  
  여기에서 추가적으로 struct에 메서드를 추가할 수 있습니다.
  
  ``` go
  // 위에서 이어집니다.
  	//👇이 부분이 구조체를 참조한다는 **receiver** 입니다.
  func (c computer) AddStorage (storage int) {
      c.storage += storage
  }
  ```
  ```go
  func main () {
  	myCom := computer{cpu: "i5", ram: 16, hdd: false, ssd: true, storage: 256+512}
  	myCom.AddStorage(256)
  	fmt.Println(myCom)
      // result > {i5 16 false true 768}
  }
  ```
  
  여기서 참조해야할 것은 `receiver`부분인 `(c computer)`부분입니다.
  
  이름은 마음대로 해도 되지만, 구조체의 첫번째 글자의 소문자로 규약되어있습니다.
  
  하지만 이 함수는 `computer`를 ***새로 만들어 복사합니다***(Call by value)
  
  그래서 결과가 함수를 실행해도 달라지지 않습니다.
  
  하지만 해당 호출한 구조체에서 실행하게끔 만들어주려면
  포인터로 `receiver`를 지정하여 주소를 참조하게 해줍시다.(Call by reference)
  
  ```go
  //위 함수를 수정합니다.
  	//👇이 부분의 `*`이 추가됩니다.
  func (c *computer) AddStorage (storage int) {
      c.storage += storage
  }
  //main문 동일
  //result > {i5 16 false true 1024}
  ```
  
  결과 값이 달라진것을 확인할 수 있습니다.
  
  - 접근자
  
    또, 주의할점이 한가지있다.
  
    이렇게 export (따로 적진 않았지만, 다른곳에서 impor)한다면,
    첫 글자가 대문자가 아닐 시, **외부에서 접근하지 못하게됩니다.**
  
    함수, 변수, 구조체 모두 마찬가지입니다.
  
    ``` go
    package computer
    
    type computer struct //'c'omputer 이므로 private 형식으로 간주됩니다.
    type Computer struct //'C'omputer이므로 외부에서 확인할 수 있습니다.
    ```
  
    
  
    ```go
    package main
    
    import "github.com/..../computer" //대략적으로 적었습니다.
    
    func main {
        yourComputer := computer.computer{} //접근 불가
        myComputer := computer.Computer{} //요런식으로 접근하게됩니다.
    }
    ```
  
    이런식으로 첫글자가 대문자여야 public 처럼 사용하게됩니다.
  
    읽기는 힘들지만, 이러한 규칙으로 접근자를 지정합니다.
  
  - 자동 호출 메서드
  
    Go는 해당 `struct`를 호출(출력)하게되면 (예 : `fmt.println(myComputer))`)어떻게 될까?
  
    당연하게 그냥 출력된다 (예 : `&{i5 16 ....(중략)})`)
  
    이럴때, 내가 원하는걸 출력하게 하고싶다면, 해당 구조체에 `String()`함수를 선언하여 구현하자.
  
    그럼 내부에서 자동으로 `String()`함수를 호출하여 반환할 것이다.
  
  - `type Computer map[string]string`
  
    위 선언은 어떻게 될까?
  
    단순히 `alias`(별칭)역할이다. map으로 동작하는 Computer를 하나의 별칭으로 사용하게 되는 것이다.
  
    그러므로 `mycom := Computer{}`로 지정하여 `map`으로 작동하는 `mycom`이 생성된다.
  
    그러므로 `mycom["cpu"] = "i7"` 가 가능해진다.
  
  

***

  

# :innocent:이 문서는 현재 작성 중입니다. 

~~거슬리시겠지만 저도 왜 계속 적는 말투가 바뀌는지 모르겠습니다 :innocent:~~

