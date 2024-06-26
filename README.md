# *batch-estate-engine* 🏢

반갑습니다! 오늘도 힘찬 하루인가요?  
*batch-estate-engine* 은 **약 1000만건의 부동산 데이터를 처리하는 배치 어플리케이션**입니다.  
[공공데이터포털](https://www.data.go.kr/)에서 제공하는 종류별 부동산 **데이터를 처리하고, 저장하고, API로 제공합니다.**

이 프로젝트의 주요 목적은 아래와 같습니다.

1. **대용량 데이터 관리**
2. **멱등성 보장**
3. **테스트 주도 개발(TDD)**

## 아키텍쳐 🗺️

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

### 2. 배치

#### 핵심 기능들을 일괄처리

- [X] 한번에 법정동코드에 해당하는 모든 데이터 처리
- [X] 재실행 방지
- [X] 멱등성 보장

### 3. 스케쥴링

- [ ] 매월 1일 `00:00 KST`에 법정동 코드 갱신
- [ ] 매월 1일 `00:00 KST`에 배치 Job 실행

## 202401 개선 사항 🚀

- [X] 테스트시에는 H2 Database 사용
- [X] ReadMe 작성
- [X] step 실행 오류정보 로깅

## 202402 개선 사항 🚀

- [X] 아파트 전월세 모든 데이터 호출
- [X] api 호출 시 파라미터의 특성을 고려한 파티셔닝 사용
- [X] 아키텍처 구조 개선 - 배치 관련 클래스 패키지 분리

## 202403 개선 사항 🚀

- [X] 멱등성 구현 - 50개의 step 실패시 job 종료
- [X] 재실행 방지 기능 확인

## 202404 개선 사항 🚀

- [X] 종류별 부동산 데이터 호출 - 아파트 매매, 아파트 전월세, 단독다가구 전월세, 연립다세대 전월세, 오피스텔 전월세
- [X] 재시도 로직 추가
- [X] Amazon Web Service에 배포
  - [X] RDS 생성 및 EC2와 local에 연결
  - [X] Ec2 생성 및 프로젝트 빌드
  - [X] 약 2800만 건의 데이터 호출 완료

## 202405 개선할 사항 🚀

- [X] 부동산 데이터 테이블 설계 (인덱스, 컬럼 사이즈, 유형, 디폴트, NOT NULL, PK 고려)
  - [X] '부동산 종류' 컬럼으로 인덱스 생성
  - [X] 컬럼 데이터 타입, 사이즈 조정
  - [X] 무결성을 위한 DEFAULT, NOT NULL 추가
  - [X] PK BIGINT -> INT로 변경
- [ ] 관심사 분리 / 역할 분리 (한 클래스는 한가지 역할 / 한 메서드는 한가지 기능만)
  - [] GenerateApiQueryParam 클래스
  - [] RealEstateDataFetcher 클래스