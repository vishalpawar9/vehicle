package com.sipl.vehicle.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
@Audited
public class Vehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
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
