-- V3__First_optimization_column_data_type.sql

# 숫자 데이터의 쉼표 제거
UPDATE batch_estate_engine.real_estate
SET deal_amount    = REPLACE(deal_amount, ',', ''),
    deposit        = REPLACE(deposit, ',', ''),
    deposit_before = REPLACE(deposit_before, ',', '');

# 공백문자 제거
UPDATE batch_estate_engine.real_estate
SET deal_amount         = TRIM(deal_amount),
    deposit             = TRIM(deposit),
    deposit_before      = TRIM(deposit_before),
    monthly_rent        = TRIM(monthly_rent),
    monthly_rent_before = TRIM(monthly_rent_before)
WHERE TRIM(deal_amount) != deal_amount
   OR TRIM(deposit) != deposit
   OR TRIM(deposit_before) != deposit_before
   OR TRIM(monthly_rent) != monthly_rent
   OR TRIM(monthly_rent_before) != monthly_rent_before;

# 숫자 형식(Decimal, Int)로 캐스팅
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
    MODIFY COLUMN monthly_rent_before DECIMAL,
    MODIFY COLUMN created_at DATETIME(6);