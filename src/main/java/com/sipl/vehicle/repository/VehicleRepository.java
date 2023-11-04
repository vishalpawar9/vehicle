package com.sipl.vehicle.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sipl.vehicle.model.Vehicle;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
	Optional<Vehicle> findByVehicleRegistrationNumber(String vehicleRegistrationNumber);

	List<Vehicle> findAllByCreationTimeBetween(LocalDateTime startDate, LocalDateTime endDate);


	long count();


}
