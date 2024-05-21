-- V1__Initial_setup.sql
CREATE TABLE batch_estate_engine.real_estate
(
    id                    BIGINT AUTO_INCREMENT PRIMARY KEY,
    cancel_deal_day       VARCHAR(255) NULL COMMENT '해제사유발생일',
    cancel_deal_type      VARCHAR(255) NULL COMMENT '해제여부',
    agent_address         VARCHAR(255) NULL COMMENT '중개사소재지',
    construct_year        VARCHAR(255) NULL COMMENT '건축년도',
    contract_period       VARCHAR(255) NULL COMMENT '계약기간',
    contract_type         VARCHAR(255) NULL COMMENT '계약구분',
    contract_year         VARCHAR(255) NULL COMMENT '년',
    created_at            DATETIME(6)  NULL,
    days                  VARCHAR(255) NULL COMMENT '일',
    deal_amount           VARCHAR(255) NULL COMMENT '거래금액',
    deal_type             VARCHAR(255) NULL COMMENT '거래유형',
    deposit               VARCHAR(255) NULL COMMENT '보증금액, 보증금',
    deposit_before        VARCHAR(255) NULL COMMENT '종전계약보증금',
    floor                 VARCHAR(255) NULL COMMENT '층',
    jeon_yong_area        VARCHAR(255) NULL COMMENT '전용면적, 계약면적',
    legal_dong            VARCHAR(255) NULL COMMENT '법정동',
    months                VARCHAR(255) NULL COMMENT '월',
    monthly_rent          VARCHAR(255) NULL COMMENT '월세금액, 월세',
    monthly_rent_before   VARCHAR(255) NULL COMMENT '종전계약월세',
    name                  VARCHAR(255) NULL COMMENT '아파트, 연립다세대, 단지',
    parcel_number         VARCHAR(255) NULL COMMENT '지번',
    real_estate_type      VARCHAR(255) NULL COMMENT '부동산 종류',
    region_code           VARCHAR(255) NULL COMMENT '지역코드',
    request_renewal_right VARCHAR(255) NULL COMMENT '갱신요구권사용',
    si_gun_gu             VARCHAR(255) NULL COMMENT '시군구'
);

CREATE INDEX idx_real_estate_type ON batch_estate_engine.real_estate (real_estate_type);
