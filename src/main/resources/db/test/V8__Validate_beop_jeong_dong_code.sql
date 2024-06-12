# 법정동 코드 데이터의 최대 길이는 5자리 숫자 -> int 형으로 변환하는게 좋겠음
SELECT beop_jeong_dong_code
FROM batch_estate_engine.real_estate
ORDER BY LENGTH(beop_jeong_dong_code) DESC
LIMIT 10;

# 숫자형이 아닌 데이터가 있는 지 검증
SELECT beop_jeong_dong_code
FROM batch_estate_engine.real_estate
WHERE beop_jeong_dong_code NOT REGEXP '^[0-9]+$';