package com.sipl.vehicle.controller.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.net.HttpHeaders;
import com.lowagie.text.DocumentException;
import com.sipl.vehicle.apiresponse.VehicleApiResponse;
import com.sipl.vehicle.controller.VehicleController;
import com.sipl.vehicle.dto.VehicleDto;
import com.sipl.vehicle.service.VehicleService;

@RestController
public class VehicleControllerImpl<Vehicle> implements VehicleController {
	@Autowired
	private final VehicleService vehicleService;

	@Autowired
	public VehicleControllerImpl(final VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	@Override
	public ResponseEntity<VehicleApiResponse> createVehicle(@RequestBody final VehicleDto vehicleDto) {
		final VehicleApiResponse response = vehicleService.createVehicle(vehicleDto);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<VehicleApiResponse> getAllVehicles() {
		final VehicleApiResponse response = vehicleService.getAllVehicles();
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<VehicleApiResponse> getVehicleById(@PathVariable final Integer id) {
		final VehicleApiResponse response = vehicleService.getVehicleById(id);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<VehicleApiResponse> getVehicleByVehicleRegistrationNumber(
			@PathVariable final String vehicleRegistrationNumber) {
		final VehicleApiResponse response = vehicleService
				.getVehicleByVehicleRegistrationNumber(vehicleRegistrationNumber);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<VehicleApiResponse> updateVehicle(
			@PathVariable final Integer id,
			@RequestBody final VehicleDto updatedVehicleDto) {
		final VehicleApiResponse response = vehicleService.updateVehicle(id, updatedVehicleDto);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<VehicleApiResponse> deleteVehicle(@PathVariable final Integer id) {
		final VehicleApiResponse response = vehicleService.deleteVehicle(id);
		return new ResponseEntity<>(response, response.getStatus());
	}

	@Override
	public ResponseEntity<InputStreamResource> download() throws IOException {
		String filename = "vehicles.xlsx";
		ByteArrayInputStream inputStream = vehicleService.getDataDownloaded();
		InputStreamResource response = new InputStreamResource(inputStream);
		ResponseEntity<InputStreamResource> responseEntity = ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(response);
		return responseEntity;
	}

	@Override
	public ResponseEntity<VehicleApiResponse> exportPdf(HttpServletResponse httpServletResponse,
			@RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime startDate,
		    @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") LocalDateTime endDate) 
			throws DocumentException, IOException, org.dom4j.DocumentException {
	vehicleService.generatePdfFile(httpServletResponse, startDate, endDate);
	return null;	
	}
}
