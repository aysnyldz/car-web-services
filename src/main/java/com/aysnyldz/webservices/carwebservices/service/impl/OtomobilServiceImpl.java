package com.aysnyldz.webservices.carwebservices.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;
import com.aysnyldz.webservices.carwebservices.repository.OtomobilRepository;
import com.aysnyldz.webservices.carwebservices.service.OtomobilService;
import com.aysnyldz.webservices.carwebservices.util.GenericSpesification;
import com.aysnyldz.webservices.carwebservices.util.SearchCriteria;
import com.aysnyldz.webservices.carwebservices.util.SearchSpecification;

@Service
public class OtomobilServiceImpl implements OtomobilService {


	@Autowired
	OtomobilRepository otomobilRepository;

	
	@Override
	public Iterable<Otomobil> save(List<Otomobil> cars) {
		return otomobilRepository.saveAll(cars);
	}

	@Override
	public List<Otomobil> findAll() {
		return otomobilRepository.findAll();
	}

	@Override
	public List<Otomobil> searchCriteria(List<SearchCriteria> criterias) {

		GenericSpesification<Otomobil> genericSpesification = new GenericSpesification<Otomobil>();
		
		for (SearchCriteria searchCriteria : criterias) {
			genericSpesification.add(searchCriteria);
		}
		
		return otomobilRepository.findAll(genericSpesification);
	}

	@Override
	public List<Otomobil> searchKey(List<SearchCriteria> criterias) {

		SearchSpecification<Otomobil> searchSpecification = new SearchSpecification<Otomobil>();

		for (SearchCriteria searchCriteria : criterias) {
			searchSpecification.add(searchCriteria);
		}

		return otomobilRepository.findAll(searchSpecification);

	}

}
