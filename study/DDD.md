# DDD

##### Domain Driven Design

## Domain

도메인이란 어플리케이션 내의 로직들이 관여하는 정보와 활동의 영역

## 목적

1. 핵심 도메인과 그 기능에 집중
2. 도메인의 모델을 정교하게 구축
3. 어플리케이션 모델을 발전시키고 새롭게 생기는 도메인 관련 이슈 해결

## Strategic Design

소프트웨어를 디자인할 때, 객체를 기준으로 디자인 하는것을 Object Oriented Design이라 한다. 이러한 관점에서 Strategic Design (디자인 플래닝)은 OOD가 잘된것이라 볼수 있다.

Strategic Design이란 Context에 대해 생각하고 이를 기준으로 디자인하는 것을 말한다.

Context는 특정 객체 혹은 상황이 벌어지는 주변 환경을 말하는데,
예를들어, 가게안의 시식코너의 음식은 무료지만, 냉장고 안의 음식은 유료인것, 상황에 따라 가격이 변동되는 것 이런 특성과 환경을 말한다.

이처럼 같은 사물이나 행동양상이 벌어지는 상황에 집중하여 디자인을 하는 것이 Strategic Design의 핵심이다.

## DDD

우리가 집을 짓는다고 생각해보자

1. 어떤 주택을 지을지 생각한다.
2. 집 전문가와 상의한다 (Domin Expert)
3. 핵심가치를 집중하여 집을 짓는데 강조한다.
4. 그 뒤, 이미 지어진 집들을 많이 조사하고, 마음에 드는 집을 형상을 그린다
5. 이를 토대로 설계도를 그려나간다.

이렇게 집을 짓는다면, DDD 관점에서

- 집에 대한 설계 전체를 domain이라 부른다.
- 커다린 집의 부분들을 subDomain이라 한다.
- 실제 subDomain의 구체적인 형상을 나타내는것을 domain model이라 한다.

이처럼 Domain뿐만아니라 여러가지 나타내는 단어들이 더있다.

- Context 

  의미를 결정하는 것 처럼 보이는 단어나 문장이 나타는 설정으로,
  모델에 관한 문장은context안에서만 다룬다.

  예시에 들자면 주택을 구성하는 각 부분 구간들에 대한 환경을 의미한다

- Model

  도메인의 특정 양상을 묘사하는 추상화 시스템으로
  도메인과 관련된 문제를 해결하는데 사용된다.

- Ubiquitous

  domain model을 둘러싼 언어구조를 말하며, 

# 작성중... :innocent: