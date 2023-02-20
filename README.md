# MoneyCookie

## 💻 프로젝트 소개

- **프로젝트명** : MoneyCookie
- **프로젝트 기간** : 2022.11.25 ~ ( 진행 중 )
- 실시간 국내 주식 포트폴리오 관리 플랫폼

## ⚒ 사용 기술 및 개발 환경

**Frontend** : HTML, CSS, Tailwind CSS, Vue.js 3

**Backend** : JAVA 11, Spring Boot 2

**Database** : MySQL 8

**OS** : Windows 10

**Tools** : IntelliJ IDEA, MySQL Workbench 8

**Build Tool** : Gradle

## 📊 ER Diagram

<img src="./images/mc_er.png">

## 📑 주요 기능

- 유저별 국내 주식 포트폴리오 작성 가능
  - 종목명, 매수량, 매수금액, 매수날짜 작성 필요
  - 종목코드, 종목명 중 하나만 입력해서 상장 종목 정보 조회
- RestTemplate를 활용해 외부 API와 통신하여 주식 종목 정보 및 가격 등을 크롤링
- WebSocket을 사용해 개장 중일 경우 실시간 가격 변화 확인 가능
