-- beop_jeong_dong_code 컬럼에 NULL 값이 있는지 확인 -> 0 개
SELECT COUNT(*) AS beop_jeong_dong_code_null_count
FROM real_estate
WHERE beop_jeong_dong_code IS NULL;

-- jeon_yong_area 컬럼에 NULL 값이 있는지 확인 -> 49 개
SELECT COUNT(*) AS jeon_yong_area_null_count
FROM real_estate
WHERE jeon_yong_area IS NULL;

-- real_estate_type 컬럼에 NULL 값이 있는지 확인 -> 0개
SELECT COUNT(*) AS real_estate_type_null_count
FROM real_estate
WHERE real_estate_type IS NULL;