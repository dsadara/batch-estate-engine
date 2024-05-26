-- V2__First_optimization_column_name.sql
ALTER TABLE batch_estate_engine.real_estate
    RENAME COLUMN legal_dong TO beop_jeong_dong,
    RENAME COLUMN region_code TO beop_jeong_dong_code,
    RENAME COLUMN months TO contract_month,
    RENAME COLUMN days TO contract_day;