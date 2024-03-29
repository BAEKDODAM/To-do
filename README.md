<img src="image/gn0.png" />
<br>

# My Todo App
해야 할 일의 확인, 관리가 쉽도록 돕기 위한 서비스입니다.

<br>

## Contributors
- BE: 백도담 [@BAEKDODAM](https://github.com/BAEKDODAM)

<br>

## 기술 스텍
- Git
- Github
- Java
- Spring
- SpringBoot
- Spring Data JPA
- Spring Security
- JWT
- OAuth2
- JUnit 5, Hamcrest, Mockito

<br>

## Project Docs
### 화면 정의서
<img src="image/gn1.png" />

### DB Schema
<img src="image/gn2.png" width="600px" />

<br/>
<br>


## Features
### Spring Security와 JWT를 사용한 **안전하고 효율적인 인증 및 인가 구현**

<img src="image/jwt.png"/>

본 프로젝트에 Spring Security에서 JWT 인증을 사용하여 자체 회원가입, 로그인 기능을 구현하였습니다. Spring Security와 JWT를 활용하여 프로젝트에서 인증 및 인가 기능을 구현하는 것은 보안과 사용자 경험을 향상시키고 애플리케이션의 안전성을 확보하는 데 중요한 역할을 합니다.
본 프로젝트는 자체 로그인과 함께 google 로그인을 지원합니다. JWT를 사용함으로서 소셜 미디어 로그인과 같은 외부 공급자와 통합하기 용이하였으며, 단일 서명된 토큰을 사용해 사용자를 식별할 수 있습니다.


### OAuth2를 사용한 Google 로그인 구현
프로젝트에서는 사용자 경험을 향상시키고 다양한 로그인 옵션을 제공하기 위해 OAuth2 프로토콜을 활용하여 Google 로그인을 구현했습니다.
Google 로그인을 제공함으로써 사용자는 별도의 계정을 만들거나 기억할 필요 없이 Google 계정을 통해 간편하게 로그인할 수 있습니다. 또한 Google은 보안 및 신원 확인을 강화하기 위해 다양한 보안 기능을 제공하므로 사용자의 계정 정보를 더욱 안전하게 보관할 수 있습니다.
이와 같이 Google 로그인을 구현함으로써 사용자 편의성과 보안을 동시에 고려한 솔루션을 제공하였으며, OAuth2를 사용하여 다양한 소셜 로그인 옵션을 통합하는 경험을 얻었습니다.


### Junit5, Hamcrest, Mockito를 사용한 테스트 코드 구현
프로젝트에서는 JUnit 5, Hamcrest, Mockito와 같은 테스트 도구를 활용하여 코드가 안정적인지 테스트하였습니다. 
JUnit 5는 자바 언어를 위한 표준 테스팅 프레임워크로, 테스트 케이스를 작성하고 실행하는 데 사용되었습니다. 다양한 어노테이션과 어서션을 제공하여 테스트 코드를 효율적으로 작성할 수 있었습니다.
Hamcrest는 JUnit과 함께 사용되는 소프트웨어 테스트를 위한 자바 라이브러리입니다. Hamcrest를 사용하여 테스트 코드의 가독성과 간결성을 향상 시켰습니다.
Mockito는 Mock 객체를 생성하고 조작하는 도구로, 의존성 주입을 모의 객체로 대체하여 테스트를 수행할 수 있게 해주었습니다. 실제 의존성을 갖지 않으면서 테스트를 진행할 수 있는 장점을 활용하였습니다.
결과적으로 JUnit 5, Hamcrest, Mockito와 같은 테스트 도구를 활용하여 효율적인 테스트 케이스를 작성하고 코드 품질을 유지할 수 있었습니다.

<br>
