package com.aysnyldz.webservices.carwebservices.service;

import java.util.List;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;
import com.aysnyldz.webservices.carwebservices.util.SearchCriteria;

public interface OtomobilService {
	
	Iterable<Otomobil> save(List<Otomobil> cars);
	
	List<Otomobil> findAll();
	
    List<Otomobil> searchCriteria(List<SearchCriteria> criterias);
    
    List<Otomobil> searchKey(List<SearchCriteria> criterias);
            
}
