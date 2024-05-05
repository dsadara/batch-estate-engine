SET profiling = 1;

SELECT COUNT(*)
FROM real_estate;

SELECT real_estate_type, COUNT(*) AS count
FROM real_estate
WHERE real_estate_type IN ('APT_RENT', 'APT_TRADE', 'OFFICETEL_RENT', 'DETACHEDHOUSE_RENT', 'ROWHOUSE_RENT')
GROUP BY real_estate_type;

SHOW PROFILES;
