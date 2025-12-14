## Neurous-Server-Repo

### 개발 환경 세팅

#### 1. gradle 의존성 설치

``` ./gradlew build ```

### 2. Husky & Linit - staged 설정

#### Husky hook 활성화

``` npx husky install ```

#### lint-staged 의존성 설치

``` npm install ```

> 이 프로젝트는 코드 품질을 유지하기 위해 Husky를 사용하여 Git hooks를 관리합니다. <br>
> staged된 파일만 lint 검사 수행합니다

#### 서버 실행 (기본 포트: 8080)

``` ./gradlew bootRun  ```

### 브렌치 컨벤션

> Husky pre-push hook 으로 브렌치 이름 검증을 수행합니다

- 형식
    - 허용타입 : `feat` , `fix`,   `docs`, `refactor`, `perf`, `test`, `chore`
    - 예시
        - feat/login
        - fix/chat

### 커밋 컨벤션

```
#이슈번호 타입: 메시지
```

### 커밋 타입

- feat → 새로운 기능 추가
- fix → 버그 수정
- refactor → 코드 리팩토링
- docs → 문서 수정
- style → 코드 스타일 수정 (포맷, 세미콜론 등)
- test → 테스트 코드 작성/수정
- chore → 빌드, 설정, 패키지 관리 등 잡다한 수정
- perf → 성능 개선
- ci → CI/CD 관련 설정
- revert → 이전 커밋 되돌리기

### 예시

```
feat: 로그인 기능 구현
fix: 로그인 로직 버그 수정
```

