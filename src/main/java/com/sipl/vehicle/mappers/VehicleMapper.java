package com.sipl.vehicle.mappers;

import org.mapstruct.Mapper;

import com.sipl.vehicle.dto.VehicleDto;
import com.sipl.vehicle.model.Vehicle;

@Mapper(componentModel = "spring")
public interface VehicleMapper {
	VehicleDto toDto(Vehicle updatedVehicle);

	Vehicle toEntity(VehicleDto vehicleDto);
}
