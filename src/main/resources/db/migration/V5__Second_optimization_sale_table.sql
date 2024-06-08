-- V5__Second_optimization_sale_table.sql

# sale 테이블 생성
CREATE TABLE sale
(
    id               INT NOT NULL,
    cancel_deal_day  VARCHAR(255) COMMENT '해제사유 발생일',
    cancel_deal_type VARCHAR(255) COMMENT '해제여부',
    agent_address    VARCHAR(255) COMMENT '중개사소재지',
    deal_amount      DECIMAL COMMENT '거래금액',
    deal_type        VARCHAR(255) COMMENT '거래유형',
    PRIMARY KEY (id),
    FOREIGN KEY (id) REFERENCES real_estate (id)
);

# '아파트 매매' 타입의 데이터 입력
INSERT INTO sale (id, cancel_deal_day, cancel_deal_type, agent_address, deal_amount, deal_type)
SELECT id, cancel_deal_day, cancel_deal_type, agent_address, deal_amount, deal_type
FROM real_estate
WHERE real_estate_type = 'APT_TRADE';