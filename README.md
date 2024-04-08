# 미국 주식 RSS 뉴스 피드 프로젝트
* 미국 주식장이 열리기전 관심이 있는 회사에 대한 뉴스를 매일 확인 할 수 있게 만들었습니다.
### 기능 소개
1. 자동 뉴스 수집: 미국 주식 시장이 열리는 시간에 맞춰, 스프링 부트 기반의 스케줄러를 통해 매일 자동으로 미국 주식 뉴스를 API를 통해 수집합니다.
2. 맞춤형 뉴스 발송: 수집된 뉴스는 시장 개장 후 30분 뒤, 사용자가 관심 있어 하는 특정 회사의 뉴스로 필터링되어 사용자가 등록한 이메일로 발송됩니다.
3. 카카오톡 메시지 알림: 카카오톡 OAuth 2.0을 통해 가입한 사용자는 선택한 회사의 뉴스를 카카오톡 메시지로 직접 받아볼 수 있습니다.

![readme.gif](src%2Fmain%2Fresources%2Fstatic%2Freadme.gif)
### 클라이언트 스펙
* React.js
* React-router-dom
* Context API

### 백엔드 스펙
* SpringBoot
* MariaDB
* Redis
* Webflux
* SpringSecurity
* OAuth 2.0
* SMTP

