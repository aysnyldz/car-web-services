package com.aysnyldz.webservices.carwebservices.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Otomobil {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String marka;

	private String model;

	private String sinif;

	public Otomobil() {
	}
	
	public Otomobil(Long id, String marka, String model, String sinif) {
		super();
		this.id = id;
		this.marka = marka;
		this.model = model;
		this.sinif = sinif;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMarka() {
		return marka;
	}

	public void setMarka(String marka) {
		this.marka = marka;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSinif() {
		return sinif;
	}

	public void setSinif(String sinif) {
		this.sinif = sinif;
	}

	@Override
	public String toString() {
		return "Otomobil [id=" + id + ", marka=" + marka + ", model=" + model + ", sinif=" + sinif + "]";
	}

	

}
