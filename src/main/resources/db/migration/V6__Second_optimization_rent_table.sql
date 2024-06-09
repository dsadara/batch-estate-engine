-- V6__Second_optimization_rent_table.sql

# rent 테이블 생성
CREATE TABLE rent
(
    id                    INT NOT NULL,
    request_renewal_right VARCHAR(255) COMMENT '갱신요구권사용',
    contract_type         VARCHAR(255) COMMENT '계약구분',
    contract_period       VARCHAR(255) COMMENT '계약기간',
    deposit               DECIMAL COMMENT '보증금',
    deposit_before        DECIMAL COMMENT '종전계약보증금',
    monthly_rent          DECIMAL COMMENT '월세',
    monthly_rent_before   DECIMAL COMMENT '종전 계약 월세',
    si_gun_gu             VARCHAR(255) COMMENT '시군구',
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES real_estate (id)
);

# '전월세' 타입의 데이터 입력
INSERT INTO rent (id, request_renewal_right, contract_type, contract_period, deposit, deposit_before, monthly_rent,
                  monthly_rent_before, si_gun_gu)
SELECT id,
       request_renewal_right,
       contract_type,
       contract_period,
       deposit,
       deposit_before,
       monthly_rent,
       monthly_rent_before,
       si_gun_gu
FROM real_estate
WHERE real_estate_type IN ('APT_RENT', 'OFFICETEL_RENT', 'DETACHEDHOUSE_RENT', 'ROWHOUSE_RENT');