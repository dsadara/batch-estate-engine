-- V9__First_optimization_add_constraint.sql

# null 값이 들어 있는 컬럼에 default 값 넣기
UPDATE real_estate
SET jeon_yong_area = '0'
WHERE jeon_yong_area IS NULL;

# 제약 조건 추가
ALTER TABLE real_estate
    MODIFY COLUMN beop_jeong_dong_code INT NOT NULL DEFAULT 0,
    MODIFY COLUMN jeon_yong_area VARCHAR(255) NOT NULL DEFAULT '0',
    MODIFY COLUMN real_estate_type VARCHAR(255) NOT NULL;
