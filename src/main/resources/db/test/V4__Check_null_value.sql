-- V4 실행 전 체크

select count(*)
from real_estate; # 전체개수 28602603

select count(*)
from real_estate
where beop_jeong_dong_code is null; # null인 법정동 코드 -> 0 개

select count(*)
from real_estate
where jeon_yong_area is null; # null인 전용면적 -> 49 개

select count(*)
from real_estate
where name is null; # null인 이름 -> 5383185 개

