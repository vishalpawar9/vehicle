package com.sipl.vehicle.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDto {
	private int id;
	private String vehicleRegistrationNumber;
	private String ownerName;
	private String brand;
	private LocalDate registrationExpires;
	private boolean isActive;
	private String createdBy;
	private String modifiedBy;
	private LocalDateTime creationTime;
	private LocalDateTime modifiedTime;
}
