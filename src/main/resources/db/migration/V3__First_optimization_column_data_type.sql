-- V3__First_optimization_column_data_type.sql

# 숫자 데이터의 쉼표 제거
UPDATE batch_estate_engine.real_estate
SET deal_amount    = REPLACE(deal_amount, ',', ''),
    deposit        = REPLACE(deposit, ',', ''),
    deposit_before = REPLACE(deposit_before, ',', '');

# 공백문자 제거
UPDATE batch_estate_engine.real_estate
SET deal_amount         = REPLACE(deal_amount, ' ', NULL),
    deposit             = REPLACE(deposit, ' ', NULL),
    deposit_before      = REPLACE(deposit_before, ' ', NULL),
    monthly_rent        = REPLACE(monthly_rent, ' ', NULL),
    monthly_rent_before = REPLACE(monthly_rent_before, ' ', NULL),
    contract_year       = REPLACE(contract_year, ' ', NULL),
    contract_month      = REPLACE(contract_month, ' ', NULL),
    contract_day        = REPLACE(contract_day, ' ', NULL),
    construct_year      = REPLACE(construct_year, ' ', NULL),
    floor               = REPLACE(floor, ' ', NULL);


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
    MODIFY COLUMN monthly_rent_before DECIMAL;