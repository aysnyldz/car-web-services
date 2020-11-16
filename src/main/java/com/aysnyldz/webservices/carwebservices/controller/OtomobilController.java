package com.aysnyldz.webservices.carwebservices.controller;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;
import com.aysnyldz.webservices.carwebservices.service.OtomobilService;
import com.aysnyldz.webservices.carwebservices.util.SearchCriteria;
import com.aysnyldz.webservices.carwebservices.util.SearchOperation;

@RestController
@RequestMapping("/otomobil")
public class OtomobilController {

	@Autowired
	private OtomobilService otomobilService;

	private static final String MARKA = "marka";

	private static final String MODEL = "model";

	private static final String SINIF = "sinif";

	@GetMapping("/all")
	public List<Otomobil> findAll() {
		return otomobilService.findAll();
	}

	@GetMapping("/searchCriteria")
	public List<Otomobil> findAll(@RequestParam(value = "search", required = false) String search) {
		
		String encodedParam = convertStringToBase64(search);	
			
		return otomobilService.searchCriteria(createSearchCriteria(encodedParam, SearchOperation.EQUAL));

	}

	@GetMapping("/searchKey")
	public List<Otomobil> searchKey(@RequestParam(value = "key", required = false) String key) {
		
		String encodedParam = convertStringToBase64(key);
		
		return otomobilService.searchKey(createSearchCriteria(encodedParam, SearchOperation.MATCH));
	}


	private List<SearchCriteria> createSearchCriteria(String url, SearchOperation operation) {

		url = convertStringFromBase64(url);
		
		List<SearchCriteria> params = new ArrayList<SearchCriteria>();

		if (url != null) {
			

			Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?);");
			
// 			with turkish char			
//			Pattern pattern = Pattern.compile("(\\w+?[\\p{L}\\s]+)(:|<|>)(\\w+?[\\p{L}\\s]+);");
			
//			with escape char 
//			Pattern pattern = Pattern.compile("(\\w+?[\\p{L}\\s/\\\\&]+)(:|<|>)(\\w+?[\\p{L}\\s/\\\\&]+);");

			Matcher matcher = pattern.matcher(url + ";");

			System.out.println(matcher.groupCount());
			
			while (matcher.find()) {

				String category = matcher.group(1);

				if (category.toString().equals("hepsi")) {

					params.add(new SearchCriteria(SearchOperation.ALL));
					params.add(new SearchCriteria(MARKA, operation, matcher.group(3)));
					params.add(new SearchCriteria(MODEL, operation, matcher.group(3)));
					params.add(new SearchCriteria(SINIF, operation, matcher.group(3)));

				} else {

					params.add(new SearchCriteria(category, operation, matcher.group(3)));

				}
			}
		}

		return params;
	}
	
	private String convertStringToBase64(String input) {
		return Base64.getEncoder().encodeToString(input.getBytes());
	}

	private String convertStringFromBase64(String input) {
		return new String(Base64.getDecoder().decode(input));
	}

}
