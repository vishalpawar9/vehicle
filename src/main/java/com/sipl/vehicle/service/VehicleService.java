package com.sipl.vehicle.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lowagie.text.DocumentException;
import com.sipl.vehicle.apiresponse.VehicleApiResponse;
import com.sipl.vehicle.dto.VehicleDto;

@Service
public interface VehicleService {
	VehicleApiResponse createVehicle(VehicleDto vehicleDto);

	VehicleApiResponse updateVehicle(Integer id, VehicleDto updatedVehicleDto);

	VehicleApiResponse deleteVehicle(Integer id);

	VehicleApiResponse getVehicleById(Integer id);

	VehicleApiResponse getVehicleByVehicleRegistrationNumber(String vehicleRegistrationNumber);

	VehicleApiResponse getAllVehicles();

	ByteArrayInputStream getDataDownloaded() throws IOException;

	VehicleApiResponse generatePdfFile(HttpServletResponse httpServletResponse, LocalDateTime startDate,
			LocalDateTime endDate) throws DocumentException, IOException, org.dom4j.DocumentException;

}