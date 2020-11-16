package com.aysnyldz.webservices.carwebservices.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;


public interface OtomobilRepository extends JpaRepository<Otomobil, Long>, JpaSpecificationExecutor<Otomobil>{
		
}
