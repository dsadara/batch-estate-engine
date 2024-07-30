-- V1__add_auto_increment_to_real_estate.sql

-- Check if the id column already has AUTO_INCREMENT
-- If not, then modify the column to add AUTO_INCREMENT
ALTER TABLE real_estate
    MODIFY COLUMN id INT AUTO_INCREMENT;