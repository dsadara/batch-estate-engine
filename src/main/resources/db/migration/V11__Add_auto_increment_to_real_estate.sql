-- V11__Add_auto_increment_to_real_estate.sql

ALTER TABLE real_estate
    MODIFY COLUMN id INT AUTO_INCREMENT;