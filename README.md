# *batch-estate-engine* 🏢

일괄 처리로 부동산 데이터를 수집하는 모듈입니다. **Spring Batch의 chunk 지향 처리 기능**을 사용하여 구현했습니다.

chunk 지향 처리를 담당하는 step은 3가지로 구성되어 있습니다.

1. **ItemReader**: 공공데이터포털 API를 호출해서 얻은 Json 데이터를 DTO로 파싱합니다.
2. **ItemProcessor**: DTO를 Entity로 변환합니다.
3. **ItemWriter**: chunk size만큼 Entity가 모이면 한 번에 트랜잭션을 진행하여 데이터베이스에 저장합니다.

또한 테이블 최적화를 수행하기 위해 **Flyway로 마이그레이션**을 진행하였습니다.

*프로젝트에 대한 자세한 내용과 문제 해결 과정은 [Wiki](https://github.com/dsadara/batch-estate-engine/wiki)에서 참고하실 수 있습니다.*

### Backend

- **JDK**: 1.8
- **Spring Boot**: 2.7.11
  - **주요 기술 스택**: Spring Batch, RestTemplate, Jackson, Spring Data JPA, Junit5

### Infra

- **Public Cloud**
  - **Amazon EC2**: t2.micro (Amazon Linux 2023)
  - **Amazon RDS**: db.t3.micro (MariaDB 10.11)
- **Local Environment**
  - **Apple M2 Pro** (macOS Ventura 13.4.1)
  - **H2 Database**: 1.4.200

### Tools

- **Migration**: Flyway 8.5.13
- **Development**: Intellij IDEA Ultimate