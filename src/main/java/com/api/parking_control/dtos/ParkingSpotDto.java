package com.api.parking_control.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ParkingSpotDto {

	@NotBlank //não nulo/vazio
	private String parkingSpotNumber; // numero da vaga

	@NotBlank
	@Size(max = 7)
	private String licensePlateCar; // placa veiculo

	@NotBlank
	private String brandCar; // marca veiculo

	@NotBlank
	private String modelCar; // modelo veiculo

	@NotBlank
	private String colorCar; // cor veiculo

	@NotBlank
	private String responsibleName; // nome do responsavel

	@NotBlank
	private String apartment; // apartamento

	@NotBlank
	private String block; // bloco

	
	//GETTER E SETTERS
	public String getParkingSpotNumber() {
		return parkingSpotNumber;
	}

	public void setParkingSpotNumber(String parkingSpotNumber) {
		this.parkingSpotNumber = parkingSpotNumber;
	}

	public String getLicensePlateCar() {
		return licensePlateCar;
	}

	public void setLicensePlateCar(String licensePlateCar) {
		this.licensePlateCar = licensePlateCar;
	}

	public String getBrandCar() {
		return brandCar;
	}

	public void setBrandCar(String brandCar) {
		this.brandCar = brandCar;
	}

	public String getModelCar() {
		return modelCar;
	}

	public void setModelCar(String modelCar) {
		this.modelCar = modelCar;
	}

	public String getColorCar() {
		return colorCar;
	}

	public void setColorCar(String colorCar) {
		this.colorCar = colorCar;
	}

	public String getResponsibleName() {
		return responsibleName;
	}

	public void setResponsibleName(String responsibleName) {
		this.responsibleName = responsibleName;
	}

	public String getApartment() {
		return apartment;
	}

	public void setApartment(String apartment) {
		this.apartment = apartment;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}
	
	
	

}
