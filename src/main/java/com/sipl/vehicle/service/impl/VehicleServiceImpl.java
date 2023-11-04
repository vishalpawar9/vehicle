package com.sipl.vehicle.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sipl.vehicle.apiresponse.VehicleApiResponse;
import com.sipl.vehicle.dto.VehicleDto;
import com.sipl.vehicle.excel.ExcelUtil;
import com.sipl.vehicle.generate.pdf.PdfGenerator;
import com.sipl.vehicle.mappers.VehicleMapper;
import com.sipl.vehicle.model.Vehicle;
import com.sipl.vehicle.repository.VehicleRepository;
import com.sipl.vehicle.service.VehicleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class VehicleServiceImpl<VehicleResponse> implements VehicleService {
	@Autowired
	private final  VehicleRepository vehicleRepository;
	@Autowired
	private final  VehicleMapper vehicleMapper;
   @Autowired
   private PdfGenerator pdfGenerator;
	
	public VehicleServiceImpl(final VehicleRepository vehicleRepository, final VehicleMapper vehicleMapper) {
		this.vehicleRepository = vehicleRepository;
		this.vehicleMapper = vehicleMapper;
	}
	@Override
	public VehicleApiResponse createVehicle(final VehicleDto vehicleDto) {
		log.info("Create Vehicle");
		log.info("Vehicle Dto" + vehicleDto);
		final VehicleApiResponse response = new VehicleApiResponse();
		try {
			final Optional<Vehicle> vehicleExists = vehicleRepository
					.findByVehicleRegistrationNumber(vehicleDto.getVehicleRegistrationNumber());
			if (!vehicleExists.isPresent()) {
				Vehicle vehicle = vehicleMapper.toEntity(vehicleDto);
				vehicle.setCreationTime(LocalDateTime.now());
				vehicle.setModifiedTime(null);
				vehicle.setModifiedBy(null);
				vehicle.setActive(true);
				vehicle = vehicleRepository.save(vehicle);
				response.setMessage("Vehicle created succesfully");
				response.setVehicle(vehicleMapper.toDto(vehicle));
				response.setStatus(HttpStatus.OK);
				return response;
			} else {
				response.setMessage("vehicle Already Exist");
				response.setStatus(HttpStatus.NOT_FOUND);
				return response;
			}
		} catch (final Exception e) {
			e.printStackTrace();
			response.setMessage("Some error occured while creating a vehicle");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return response;
		}
	}
	@Override
	public VehicleApiResponse getVehicleById(final Integer id) {
		log.info("inside getVehicleById()");
		 final VehicleApiResponse response = new VehicleApiResponse();

		try {
			final Optional<Vehicle> vehicle = vehicleRepository.findById(id);

			if (vehicle.isPresent()) {
				response.setMessage("Vehicle found by ID " + id);
				response.setVehicle(vehicleMapper.toDto(vehicle.get()));
				response.setStatus(HttpStatus.OK);
			} else {
				response.setMessage("No vehicle found with ID " + id);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			response.setMessage("error occurred while Retrive the vehicle with ID " + id);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}
	
	@Override
	public VehicleApiResponse getVehicleByVehicleRegistrationNumber(final String vehicleRegistrationNumber)
	{
		final VehicleApiResponse response=new VehicleApiResponse();
	    log.info("Inside getVehicleByVehicleRegistrationNumber");
		try
		{
			final Optional<Vehicle> vehicle=vehicleRepository.findByVehicleRegistrationNumber(vehicleRegistrationNumber);
			if(vehicle.isPresent())
			{
				response.setMessage("Vehicle Found by Vehicle Registration Number  "+vehicleRegistrationNumber);
				response.setVehicle(vehicleMapper.toDto(vehicle.get()));
				response.setStatus(HttpStatus.OK);
			}else
			{
				response.setMessage("Not find Vehicle with Registration Number "+vehicleRegistrationNumber);
				response.setStatus(HttpStatus.NOT_FOUND);
			}
			
		}catch(final Exception e)
		{
			e.printStackTrace();
			response.setMessage("Error Occured while Retrive the vehicle with Registration Number");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public VehicleApiResponse getAllVehicles() {
		log.info("inside getAllVehicles()");
		final VehicleApiResponse response = new VehicleApiResponse();
		try {
			final List<Vehicle> vehicles = vehicleRepository.findAll();
			if(!vehicles.isEmpty())
			{
			final List<VehicleDto> vehicleDtos = vehicles.stream()
					.map(vehicleMapper::toDto)
					.collect(Collectors.toList());
			response.setMessage("All vehicles retrieved successfully");
			response.setVehicles(vehicleDtos);
			response.setStatus(HttpStatus.OK);
			}
			else
			{
				response.setMessage("No vehicles Found");
			}
		} catch (final Exception e) {
			e.printStackTrace();
			response.setMessage("Error occurred while Retrive all vehicles");
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public VehicleApiResponse updateVehicle(final Integer id, final VehicleDto updatedVehicleDto) {
		log.info("inside updateVehicle()");
		final VehicleApiResponse response = new VehicleApiResponse();
		final Optional<Vehicle> existingVehicleGet = vehicleRepository.findById(id);

		try {
			if (vehicleRepository.existsById(id)) {
				final Vehicle existingVehicle = existingVehicleGet.get();
				Vehicle updatedVehicle = vehicleMapper.toEntity(updatedVehicleDto);
				updatedVehicle.setActive(true);
				updatedVehicle.setId(id);
				updatedVehicle.setCreationTime(existingVehicle.getCreationTime());
				updatedVehicle.setModifiedTime(LocalDateTime.now());
				updatedVehicle = vehicleRepository.save(updatedVehicle);
				response.setMessage("Vehicle updated successfully");
				response.setVehicle(vehicleMapper.toDto(updatedVehicle));
				response.setStatus(HttpStatus.OK);
			} else {
				response.setMessage("Vehicle with ID " + id + " does not exist");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			response.setMessage("Error occurred while updating the vehicle with ID " + id);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public VehicleApiResponse deleteVehicle(final Integer id) {
		log.info("inside deleteVehicle()");
		final VehicleApiResponse response = new VehicleApiResponse();
		try {
			if (vehicleRepository.existsById(id)) {
				vehicleRepository.deleteById(id);
				response.setMessage("Vehicle with ID " + id + " has been deleted successfully");
				response.setStatus(HttpStatus.OK);
			} else {
				response.setMessage("Vehicle with ID " + id + " does not exist");
				response.setStatus(HttpStatus.NOT_FOUND);
			}
		} catch (final Exception e) {
			e.printStackTrace();
			response.setMessage("Error occurred while deleting the vehicle with ID " + id);
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

	@Override
	public ByteArrayInputStream getDataDownloaded() throws IOException {
		final List<Vehicle> vehicle=vehicleRepository.findAll();
			final ByteArrayInputStream  data = ExcelUtil.dataToExcel(vehicle);
		return data;
		
		}

	public VehicleApiResponse generatePdfFile(HttpServletResponse re , LocalDateTime startDate,
			LocalDateTime endDate)
			{
		VehicleApiResponse response = new VehicleApiResponse();
			try {
				re.setContentType("application/pdf");
				DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD:HH:MM:SS");
				String currentDateTime = dateFormat.format(new Date());
				String headerkey = "Content-Disposition";
				String headervalue = "attachment; filename=vehicle" +currentDateTime + ".pdf";
				re.setHeader(headerkey, headervalue);
				List < Vehicle > listofVehicles = vehicleRepository.findAllByCreationTimeBetween(startDate, endDate);
				pdfGenerator.generatePdf(listofVehicles, re, startDate, endDate);
				response.setMessage("Pdf Generated Successfully");
				response.setStatus(HttpStatus.OK);
			} catch (org.dom4j.DocumentException e) {
				e.printStackTrace();
				response.setMessage("Pdf File Not Generated");
			} catch (IOException e) {
				e.printStackTrace();
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return response;
			}
}