-- V8__First_optimization_convert_beop_jeong_dong_code.sql

-- 숫자 형식(Decimal, Int)로 캐스팅
ALTER TABLE batch_estate_engine.real_estate
    MODIFY COLUMN beop_jeong_dong_code INT;