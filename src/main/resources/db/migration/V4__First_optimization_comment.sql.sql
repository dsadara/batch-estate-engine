-- V4__First_optimization_comment.sql

# 코멘트 추가
ALTER TABLE batch_estate_engine.real_estate
    MODIFY COLUMN beop_jeong_dong VARCHAR(255) COMMENT '법정동',
    MODIFY COLUMN request_renewal_right VARCHAR(255) COMMENT '갱신요구권사용',
    MODIFY COLUMN construct_year SMALLINT COMMENT '건축년도',
    MODIFY COLUMN contract_type VARCHAR(255) COMMENT '계약구분',
    MODIFY COLUMN contract_period VARCHAR(255) COMMENT '계약기간',
    MODIFY COLUMN contract_year SMALLINT COMMENT '계약년도',
    MODIFY COLUMN contract_month SMALLINT COMMENT '계약월',
    MODIFY COLUMN contract_day SMALLINT COMMENT '계약일',
    MODIFY COLUMN cancel_deal_day VARCHAR(255) COMMENT '해제사유 발생일',
    MODIFY COLUMN cancel_deal_type VARCHAR(255) COMMENT '해제여부',
    MODIFY COLUMN agent_address VARCHAR(255) COMMENT '중개사소재지',
    MODIFY COLUMN deal_amount DECIMAL COMMENT '거래금액',
    MODIFY COLUMN deal_type VARCHAR(255) COMMENT '거래유형',
    MODIFY COLUMN deposit DECIMAL COMMENT '보증금',
    MODIFY COLUMN deposit_before DECIMAL COMMENT '종전계약보증금',
    MODIFY COLUMN floor SMALLINT COMMENT '층',
    MODIFY COLUMN jeon_yong_area VARCHAR(255) COMMENT '전용면적, 계약면적',
    MODIFY COLUMN monthly_rent DECIMAL COMMENT '월세',
    MODIFY COLUMN monthly_rent_before DECIMAL COMMENT '종전 계약 월세',
    MODIFY COLUMN name VARCHAR(255) COMMENT '이름',
    MODIFY COLUMN parcel_number VARCHAR(255) COMMENT '지번',
    MODIFY COLUMN beop_jeong_dong_code VARCHAR(255) COMMENT '법정동 코드',
    MODIFY COLUMN si_gun_gu VARCHAR(255) COMMENT '시군구',
    MODIFY COLUMN real_estate_type VARCHAR(255) COMMENT '부동산 종류';