# 🥢 탐식당 (Tamsikdang)

**학교 식당 식단 정보를 조회하고, 알림을 받고, 직접 메뉴를 건의할 수 있는 통합 식단 플랫폼**

> "식사 시간마다 헷갈리는 메뉴 확인, 이제는 탐식당에서 한번에!"

---

## 📌 주요 기능

- **식단 정보 실시간 조회**: 요일·식당·끼니별 식단을 사용자 친화적으로 제공
- **식단 알림 및 공지 시스템**: 식사 시작 전 자동 알림, 긴급 공지사항(품절, 메뉴변경 등) 전달 기능
- **메뉴 건의 기능**: 사용자가 직접 메뉴를 제안하고 반영 여부 확인 가능
- **관리자 시스템 분리**: 메뉴 및 식단 데이터 등록/수정, 공지사항 관리 등

---

## ⚙️ 기술 스택

| 구분       | 사용 기술                          |
|------------|------------------------------------|
| Backend    | Spring Boot, Java 17               |
| Database   | MySQL                              |
| Infra      | AWS EC2, Load Balancer, Auto Scaling Group |
| 배포       | Github Actions + AWS CodeDeploy    |
| 모니터링   | AWS CloudWatch                     |
| CDN        | AWS CloudFront (이미지 캐싱)       |

---

## 👨‍💻 담당 역할

> 백엔드 전반을 담당하며, **API 설계부터 인프라 배포 자동화까지** 책임지고 구현하였습니다.

- '식당', '식단', '메뉴', '알림' 등 핵심 도메인 모델 설계 및 RESTful API 개발
- JWT 기반 인증 + RBAC(Role Based Access Control) 기반 인가 시스템 구현
- 운영 환경과 테스트 환경 분리 → 테스트 환경에서 기능 시나리오 검증
- Github Actions + AWS CodeDeploy로 CI/CD 파이프라인 구성
- 로그인/회원가입 로직에 **전략 패턴** 적용 → 인증 관련 API 수 50% 감소
- CloudFront를 통한 이미지 캐싱 → 평균 이미지 응답 속도 132ms → 69ms 개선
- AWS CloudWatch 기반 리소스 모니터링 및 최적화 → 월 서버 비용 162달러 → 68달러 절감
- JPA Fetch Join 및 BatchSize 설정으로 N+1 문제 해결 → 성능 최적화

---

## 🚀 프로젝트 아키텍처 

![image](https://github.com/user-attachments/assets/2640b1a1-566e-4aa1-bcdd-8d4e56a6a6d2)



