package com.sipl.vehicle.apiresponse;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.sipl.vehicle.dto.VehicleDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleApiResponse  {
	private VehicleDto vehicle;
	private List<VehicleDto> vehicles;
	private HttpStatus status;
	private String message;
	private boolean error;
	private byte[] fileData;
}
