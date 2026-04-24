package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "delivery_vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryVehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vehicle_id")
	private Long vehicleId;

	@Column(name = "branch_id", nullable = false)
	private Long branchId;

	@Column(name = "vehicle_type", nullable = false)
	private String vehicleType;

	@Column(nullable = false)
	private Double capacity;

	@Column(nullable = false)
	private String status = "available";
}
