-- V2__First_optimization.sql
# data type, not null, default 제약조건 설정
ALTER TABLE batch_estate_engine.real_estate
    MODIFY COLUMN id INT NOT NULL,
    MODIFY COLUMN legal_dong VARCHAR(56) NOT NULL DEFAULT '' COMMENT '법정동',
    MODIFY COLUMN request_renewal_right VARCHAR(12) NOT NULL DEFAULT '' COMMENT '갱신요구권사용',
    MODIFY COLUMN construct_year SMALLINT NOT NULL DEFAULT 0 COMMENT '건축년도',
    MODIFY COLUMN contract_type VARCHAR(12) NOT NULL DEFAULT '' COMMENT '계약구분',
    MODIFY COLUMN contract_period VARCHAR(22) NOT NULL DEFAULT '' COMMENT '계약기간',
    MODIFY COLUMN contract_year SMALLINT NOT NULL DEFAULT 0 COMMENT '계약년도',
    MODIFY COLUMN months SMALLINT NOT NULL DEFAULT 0 COMMENT '계약월',
    MODIFY COLUMN days SMALLINT NOT NULL DEFAULT 0 COMMENT '계약일',
    MODIFY COLUMN cancel_deal_day VARCHAR(16) NOT NULL DEFAULT '' COMMENT '해제사유 발생일',
    MODIFY COLUMN cancel_deal_type VARCHAR(2) NOT NULL DEFAULT '' COMMENT '해제여부',
    MODIFY COLUMN agent_address VARCHAR(158) NOT NULL DEFAULT '' COMMENT '중개사소재지',
    MODIFY COLUMN deal_amount DECIMAL NOT NULL DEFAULT 0.0 COMMENT '거래금액',
    MODIFY COLUMN deal_type VARCHAR(24) NOT NULL DEFAULT '' COMMENT '거래유형',
    MODIFY COLUMN deposit DECIMAL NOT NULL DEFAULT 0.0 COMMENT '보증금액',
    MODIFY COLUMN deposit_before DECIMAL NOT NULL DEFAULT 0.0 COMMENT '종전계약보증금',
    MODIFY COLUMN floor VARCHAR(6) NOT NULL DEFAULT '' COMMENT '층',
    MODIFY COLUMN jeon_yong_area VARCHAR(18) NOT NULL DEFAULT '' COMMENT '전용면적(계약면적)',
    MODIFY COLUMN monthly_rent DECIMAL NOT NULL DEFAULT 0.0 COMMENT '월세',
    MODIFY COLUMN monthly_rent_before DECIMAL NOT NULL DEFAULT 0.0 COMMENT '종전 계약 월세',
    MODIFY COLUMN name VARCHAR(158) NOT NULL DEFAULT '' COMMENT '이름',
    MODIFY COLUMN parcel_number VARCHAR(26) NOT NULL DEFAULT '' COMMENT '지번',
    MODIFY COLUMN region_code VARCHAR(10) NOT NULL DEFAULT '' COMMENT '지역코드',
    MODIFY COLUMN si_gun_gu VARCHAR(42) NOT NULL DEFAULT '' COMMENT '시군구',
    MODIFY COLUMN real_estate_type VARCHAR(36) NOT NULL DEFAULT '' COMMENT '부동산 종류',
    MODIFY COLUMN created_at DATETIME(6) DEFAULT CURRENT_TIMESTAMP(6) NOT NULL;
# 컬럼명 변경
ALTER TABLE batch_estate_engine.real_estate
    RENAME COLUMN legal_dong TO beop_jeong_dong,
    RENAME COLUMN region_code TO beop_jeong_dong_code,
    RENAME COLUMN months TO contract_month,
    RENAME COLUMN days TO contract_day;