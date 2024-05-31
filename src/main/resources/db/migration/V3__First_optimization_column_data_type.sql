-- V3__First_optimization_column_data_type.sql

-- 숫자 데이터의 쉼표 제거
UPDATE batch_estate_engine.real_estate
SET deal_amount    = REPLACE(deal_amount, ',', ''),
    deposit        = REPLACE(deposit, ',', ''),
    deposit_before = REPLACE(deposit_before, ',', '');

-- 공백문자 제거 및 공백만 있는 문자열을 NULL로 변경
UPDATE batch_estate_engine.real_estate
SET deal_amount         = CASE WHEN TRIM(deal_amount) = '' THEN NULL ELSE TRIM(deal_amount) END,
    deposit             = CASE WHEN TRIM(deposit) = '' THEN NULL ELSE TRIM(deposit) END,
    deposit_before      = CASE WHEN TRIM(deposit_before) = '' THEN NULL ELSE TRIM(deposit_before) END,
    monthly_rent        = CASE WHEN TRIM(monthly_rent) = '' THEN NULL ELSE TRIM(monthly_rent) END,
    monthly_rent_before = CASE WHEN TRIM(monthly_rent_before) = '' THEN NULL ELSE TRIM(monthly_rent_before) END,
    contract_year       = CASE WHEN TRIM(contract_year) = '' THEN NULL ELSE TRIM(contract_year) END,
    contract_month      = CASE WHEN TRIM(contract_month) = '' THEN NULL ELSE TRIM(contract_month) END,
    contract_day        = CASE WHEN TRIM(contract_day) = '' THEN NULL ELSE TRIM(contract_day) END,
    construct_year      = CASE WHEN TRIM(construct_year) = '' THEN NULL ELSE TRIM(construct_year) END,
    floor               = CASE WHEN TRIM(floor) = '' THEN NULL ELSE TRIM(floor) END;

-- 숫자 형식(Decimal, Int)로 캐스팅
ALTER TABLE batch_estate_engine.real_estate
    MODIFY COLUMN id INT NOT NULL,
    MODIFY COLUMN construct_year SMALLINT,
    MODIFY COLUMN contract_year SMALLINT,
    MODIFY COLUMN contract_month SMALLINT,
    MODIFY COLUMN contract_day SMALLINT,
    MODIFY COLUMN floor SMALLINT,
    MODIFY COLUMN deal_amount DECIMAL,
    MODIFY COLUMN deposit DECIMAL,
    MODIFY COLUMN deposit_before DECIMAL,
    MODIFY COLUMN monthly_rent DECIMAL,
    MODIFY COLUMN monthly_rent_before DECIMAL;