package com.dsadara.realestatebatchservice.repository;

import com.dsadara.realestatebatchservice.entity.RealEstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealEstateRepository extends JpaRepository<RealEstate, Long> {

}
