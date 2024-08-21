package com.api.parking_control.controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.api.parking_control.dtos.ParkingSpotDto;
import com.api.parking_control.models.ParkingSpotModel;
import com.api.parking_control.services.ParkingSpotService;
import jakarta.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) // acessar de qualquer fonte
@RequestMapping("/parking-spot")
public class ParkingSpotController {

	@Autowired
	ParkingSpotService parkingSpotService;

	// Metodo para salvar os dados inseridos no DB
	@PostMapping
	public ResponseEntity<Object> saveParkingSpot(@RequestBody @Valid ParkingSpotDto parkingSpotDto) {

		if (parkingSpotService.existsByLicensePlateCar(parkingSpotDto.getLicensePlateCar())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Placa veicular já em uso!");
		}

		if (parkingSpotService.existsByParkingSpotNumber(parkingSpotDto.getParkingSpotNumber())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflito: Vaga de estacionamento em uso!");
		}

		if (parkingSpotService.existsByApartmentAndBlock(parkingSpotDto.getApartment(), parkingSpotDto.getBlock())) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Conflito: Vaga registrada neste bloco e apartamento!");
		}

		var parkingSpotModel = new ParkingSpotModel();
		BeanUtils.copyProperties(parkingSpotDto, parkingSpotModel); // conversão DTO para Model
		parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC"))); // Setando a data
		return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
	}

	
	// Metodo de listagem de todas as vagas de estacionamento
	@GetMapping
	public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots() {

		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
	}

	
	// Metodo de busca por um ID especifico
	@GetMapping("/{id}")
	public ResponseEntity<Object> getOneParkingSpot(@PathVariable(value = "id") UUID id) {

		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamemto não encontrada!");
		}
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
	}
	
	
	//Metodo para deletar por ID
	@DeleteMapping("/{id}")
	public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id") UUID id){
		
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamemto não encontrada!");
		}
		parkingSpotService.delete(parkingSpotModelOptional.get());
		return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com sucesso!!");
	}
	
	
	//Metodo para atualizar dados
	@PutMapping("{id}")
	public ResponseEntity<Object> updateParkingSpot(@PathVariable(value = "id") 
	UUID id, @RequestBody @Valid ParkingSpotDto parkingSpotDto){
		
		Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
		if (!parkingSpotModelOptional.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga de estacionamemto não encontrada!");
		}
		
		var parkingSpotModel = parkingSpotModelOptional.get();
		parkingSpotModel.setParkingSpotNumber(parkingSpotDto.getParkingSpotNumber());
		parkingSpotModel.setLicensePlateCar(parkingSpotDto.getLicensePlateCar());
		parkingSpotModel.setModelCar(parkingSpotDto.getModelCar());
		parkingSpotModel.setBrandCar(parkingSpotDto.getBrandCar());
		parkingSpotModel.setColorCar(parkingSpotDto.getColorCar());
		parkingSpotModel.setApartment(parkingSpotDto.getApartment());;
		parkingSpotModel.setBlock(parkingSpotDto.getBlock());
		parkingSpotModel.setResponsibleName(parkingSpotDto.getResponsibleName());
				
		return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
		
	}

}
