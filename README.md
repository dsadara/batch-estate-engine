# *batch-estate-engine* 🏢

*batch-estate-engine* 은 호갱예스 프로젝트의 **일괄 처리(Batch Processing)** 를 담당하는 모듈입니다.   
[공공 데이터포털 API](https://www.data.go.kr/)에서 제공하는 **약 2000만 건의 부동산 데이터를 호출하고(ItemReader), Entity로 변환하고(ItemProcessor), 영속화
하는(ItemWriter) 동작을 Job 단위로 묶고, Chunk 단위로 트랜잭션을 수행합니다.**

*프로젝트에 대한 자세한 내용과 문제 해결 과정은 [Wiki](https://github.com/dsadara/batch-estate-engine/wiki)에서 참고하실 수 있습니다.*

### Backend

- **JDK**: 1.8
- **Spring Boot**: 2.7.11
  - 주요 기술 스택: Spring Batch, RestTemplate, Jackson, Spring Data JPA, Junit5

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