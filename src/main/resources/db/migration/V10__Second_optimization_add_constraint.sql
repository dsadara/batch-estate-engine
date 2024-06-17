-- V10__Second_optimization_add_constraint.sql

-- rent 테이블의 외래 키 제약 조건 제거
ALTER TABLE rent
    DROP FOREIGN KEY rent_ibfk_1;

-- sale 테이블의 외래 키 제약 조건 제거
ALTER TABLE sale
    DROP FOREIGN KEY sale_ibfk_1;

-- rent 테이블에 새로운 외래 키 제약 조건 추가
ALTER TABLE rent
    ADD CONSTRAINT rent_ibfk_1
        FOREIGN KEY (id) REFERENCES real_estate (id)
            ON DELETE CASCADE;

-- sale 테이블에 새로운 외래 키 제약 조건 추가
ALTER TABLE sale
    ADD CONSTRAINT sale_ibfk_1
        FOREIGN KEY (id) REFERENCES real_estate (id)
            ON DELETE CASCADE;