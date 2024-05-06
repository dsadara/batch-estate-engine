# profiling 활성화
SET profiling = 1;

# 레코드 개수 조회
SELECT COUNT(*)
FROM real_estate;

# 부동산 종류별 레코드 개수 조회
SELECT real_estate_type, COUNT(*) AS count
FROM real_estate
WHERE real_estate_type IN ('APT_RENT', 'APT_TRADE', 'OFFICETEL_RENT', 'DETACHEDHOUSE_RENT', 'ROWHOUSE_RENT')
GROUP BY real_estate_type;

# profiling 결과 조회
SHOW PROFILES;

# 부동산 종류 컬럼(real_estate_type)으로 인덱스 생성
CREATE INDEX idx_real_estate_type ON real_estate (real_estate_type);

# 인덱스 조회
SHOW INDEX FROM real_estate;
