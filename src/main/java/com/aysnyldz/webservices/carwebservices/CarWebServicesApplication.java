package com.aysnyldz.webservices.carwebservices;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.aysnyldz.webservices.carwebservices.model.Otomobil;
import com.aysnyldz.webservices.carwebservices.service.OtomobilService;
import com.fasterxml.jackson.core.type.TypeReference;

@SpringBootApplication
public class CarWebServicesApplication {


	public static void main(String[] args) {
		SpringApplication.run(CarWebServicesApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(OtomobilService carService) {
		return args -> {
			// read file and write to model
			List<Otomobil> carList = new ArrayList<Otomobil>();

			try {

				InputStream inputStream = TypeReference.class.getResourceAsStream("/otomobilFile.txt");

				BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

				String line = null;

				while ((line = br.readLine()) != null) {

					Otomobil car = new Otomobil();

					String[] tokens = line.split(";");

					car.setMarka(tokens[0]);
					car.setModel(tokens[1]);
					car.setSinif(tokens[2]);

					carList.add(car);

				}

				br.close();

			} catch (

			IOException e) {
				e.printStackTrace();
			}

			carService.save(carList);
		
		};
	}

}
