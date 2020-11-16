package com.aysnyldz.webservices.carwebservices.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ActiveProfiles;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;
import com.aysnyldz.webservices.carwebservices.repository.OtomobilRepository;
import com.aysnyldz.webservices.carwebservices.service.impl.OtomobilServiceImpl;
import com.aysnyldz.webservices.carwebservices.util.SearchCriteria;
import com.aysnyldz.webservices.carwebservices.util.SearchOperation;

@ActiveProfiles("test")
class OtomobilControllerTest {

	@InjectMocks
	private OtomobilServiceImpl otomobilService;

	@Mock
	private OtomobilRepository otomobilRepository;

	private Otomobil otomobil;

	private List<Otomobil> otomobilList;

	private final String URL_PREFIX = "http://localhost:8080/otomobil";

	private final String URL_SEARCH_CRITERIA = "http://localhost:8080/otomobil/searchCriteria?search=";

	private final String URL_SEARCH_KEY = "http://localhost:8080/otomobil/searchKey?key=";

	@BeforeEach
	void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		otomobilList = new ArrayList<Otomobil>();
		otomobil = new Otomobil();
		otomobil.setId(1L);
		otomobil.setMarka("Skoda");
		otomobil.setModel("Octavia");
		otomobil.setSinif("Otomobil");

		otomobilList.add(otomobil);

	}

	@Test
	public void givenSearchCriteria_whenGettingListOfOtomobil_thenCorrect() {
		final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		params.add(new SearchCriteria("marka", SearchOperation.EQUAL, "Skoda"));
		params.add(new SearchCriteria("model", SearchOperation.EQUAL, "Octavia"));
		params.add(new SearchCriteria("sinif", SearchOperation.EQUAL, "Otomobil"));

		final List<Otomobil> results = otomobilService.searchCriteria(params);

		assertNotNull(results);

		for (Otomobil result : results) {
			assertEquals(otomobil, result);
		}
	}

	@Test
	public void givensearchKey_whenGettingListOfOtomobil_thenCorrect() {
		final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		params.add(new SearchCriteria("marka", SearchOperation.EQUAL, "o"));

		final List<Otomobil> results = otomobilService.searchKey(params);

		assertNotNull(results);

		for (Otomobil result : results) {
			assertEquals(otomobil, result);
		}
	}

	@Test
	public void givenSearchCriteriaHepsi_whenGettingListOfOtomobil_thenCorrect() {

		Otomobil otomobil2 = new Otomobil(2L, "BMW", "i4", "Otomobil");

		otomobilList.add(otomobil2);
		
		final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		params.add(new SearchCriteria("hepsi", SearchOperation.EQUAL, "Otomobil"));

		final List<Otomobil> results = otomobilService.searchCriteria(params);

		assertNotNull(results);

		for (Otomobil result : results) {
			assertEquals(otomobil, result);
		}
	}

	@Test
	public void givensearchKeyHepsi_whenGettingListOfOtomobil_thenCorrect() {
		
		Otomobil otomobil2 = new Otomobil(2L, "BMW", "i4", "Otomobil");

		otomobilList.add(otomobil2);
		
		final List<SearchCriteria> params = new ArrayList<SearchCriteria>();
		params.add(new SearchCriteria("hepsi", SearchOperation.EQUAL, "o"));

		final List<Otomobil> results = otomobilService.searchKey(params);

		assertNotNull(results);

		for (Otomobil result : results) {
			assertEquals(otomobil, result);
		}
	}

//	@Test
//	public void givenSinif_whenGettingListOfOtomobil_thenCorrect() {
//
//		final Response response = Assured.get(URL_SEARCH_CRITERIA + "marka:Skoda;sinif:Otomobil");
//		final String result = response.body().toString();
//		assertTrue(result.contains(otomobil.getMarka()));

//	}

}
