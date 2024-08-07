# *batch-estate-engine* 🏢

반갑습니다! 오늘도 힘찬 하루인가요? 💪💪❓

*batch-estate-engine* 은 **약 2000만건의 부동산 데이터를 처리하는 배치 어플리케이션**입니다.  
[공공데이터포털](https://www.data.go.kr/)에서 제공하는 종류별 부동산 **데이터를 처리하고, 저장하고, API로 제공합니다.**

이 프로젝트의 주요 목적은 아래와 같습니다.

### 1. **대규모의 데이터 관리**

**조회 시간 증가, 중복 제거** 등의 문제를 해결해볼 수 있었습니다.

### 2. **멱등성 보장**

외부 api 호출시 생기는 문제를 **재실행 정책, 로깅** 등으로 최소화 하였습니다.

### 3. **테스트 주도 개발(TDD)**

기능을 추가할 때 마다 **단위 테스트**로 검증해가며 프로젝트를 진행했습니다.

### _-> 자세한 문제 해결 과정들은 [Wiki](https://github.com/dsadara/batch-estate-engine/wiki)에서 참고하실 수 있습니다._

## 구현할 기능 📍

### 1. 핵심 기능

#### api 호출

- [X] 아파트 매매 데이터
- [X] 아파트 전월세 데이터
- [X] 단독다가구 전월세 데이터
- [X] 연립다세대 전월세 데이터
- [X] 오피스텔 전월세 데이터

#### 호출 파라미터 생성

- [X] 법정동 코드 5자리
- [X] 계약연월 6자리
- [X] 인증키

#### 응답값 파싱

- [X] Json에서 Dto로 변환

#### 데이터 저장

- [X] Dto에서 Entity로 변환후 저장

### 2. 배치 처리(일괄 처리)

- [X] 한번에 법정동코드에 해당하는 모든 데이터 처리
- [X] 재실행 방지
- [X] 멱등성 보장

### 3. 스케쥴링

- [ ] 매월 1일 `00:00 KST`에 법정동 코드 갱신
- [ ] 매월 1일 `00:00 KST`에 배치 Job 실행