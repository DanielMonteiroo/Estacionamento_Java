package com.api.parking_control.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.repositories.ParkingSpotRepository;
import jakarta.transaction.Transactional;

@Service
public class ParkingSpotService {

	@Autowired
	ParkingSpotRepository parkingSpotRepository;

	@Transactional
	public ParkingSpotModel save(ParkingSpotModel parkingSpotModel) {
		
		return parkingSpotRepository.save(parkingSpotModel);
	}

	//Metodo que verifica se a placa do veiculo ja está em uso
	public boolean existsByLicensePlateCar(String licensePlateCar) {
		
		return parkingSpotRepository.existsByLicensePlateCar(licensePlateCar);
	}

	//Metodo que verifica se a vaga de estacionamento ja esta em uso
	public boolean existsByParkingSpotNumber(String parkingSpotNumber) {
	
		return parkingSpotRepository.existsByParkingSpotNumber(parkingSpotNumber);
	}

	//Metodo que verifica se a vaga ja está registrada em algum bloco com AP
	public boolean existsByApartmentAndBlock(String apartment, String block) {
		return parkingSpotRepository.existsByApartmentAndBlock(apartment, block);
	}

	//Metodo que lista tudo
	public List<ParkingSpotModel> findAll() {
		return parkingSpotRepository.findAll();
	}

	//Metodo de busca por ID
	public Optional<ParkingSpotModel> findById(UUID id) {
		return parkingSpotRepository.findById(id);
	}

	//Metodo delete por ID
	@Transactional
	public void delete(ParkingSpotModel parkingSpotModel) {
		parkingSpotRepository.delete(parkingSpotModel);		
	}
}
