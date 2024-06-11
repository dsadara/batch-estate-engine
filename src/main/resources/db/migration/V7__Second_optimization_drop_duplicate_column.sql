-- V7__Second_optimization_drop_duplicate_column.sql

-- sale 테이블로 분리된 컬럼 삭제
ALTER TABLE real_estate
    DROP COLUMN cancel_deal_day,
    DROP COLUMN cancel_deal_type,
    DROP COLUMN agent_address,
    DROP COLUMN deal_amount,
    DROP COLUMN deal_type;

-- rent 테이블로 분리된 컬럼 삭제
ALTER TABLE real_estate
    DROP COLUMN request_renewal_right,
    DROP COLUMN contract_type,
    DROP COLUMN contract_period,
    DROP COLUMN deposit,
    DROP COLUMN deposit_before,
    DROP COLUMN monthly_rent,
    DROP COLUMN monthly_rent_before,
    DROP COLUMN si_gun_gu;