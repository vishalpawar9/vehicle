package com.sipl.vehicle.controller;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lowagie.text.DocumentException;
import com.sipl.vehicle.apiresponse.VehicleApiResponse;
import com.sipl.vehicle.dto.VehicleDto;

@RestController
@RequestMapping("/vehicle")
public interface VehicleController {

	@PostMapping("/add")
	ResponseEntity<VehicleApiResponse> createVehicle(VehicleDto vehicleDto);

	@GetMapping("/get-all")
	ResponseEntity<VehicleApiResponse> getAllVehicles();

	@GetMapping("/get/{id}")
	ResponseEntity<VehicleApiResponse> getVehicleById(Integer id);

	@GetMapping("/get/{vehicleRegistrationNumber}")
	ResponseEntity<VehicleApiResponse> getVehicleByVehicleRegistrationNumber(String vehicleRegistrationNumber);

	@PutMapping("/update/{id}")
	ResponseEntity<VehicleApiResponse> updateVehicle(Integer id, VehicleDto updatedVehicleDto);

	@DeleteMapping("/delete/{id}")
	ResponseEntity<VehicleApiResponse> deleteVehicle(Integer id);

	@GetMapping("/download")
	ResponseEntity<InputStreamResource> download() throws IOException;

	@GetMapping("/exportPdf")
	ResponseEntity<VehicleApiResponse> exportPdf(HttpServletResponse httpServletResponse, LocalDateTime startDate, LocalDateTime endDate)
			throws DocumentException, IOException, org.dom4j.DocumentException;

}
