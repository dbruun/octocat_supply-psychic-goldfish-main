package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.DeliveryVehicle;
import com.octodemo.octocatsupply.repository.DeliveryVehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery-vehicles")
@RequiredArgsConstructor
@Tag(name = "DeliveryVehicles", description = "API endpoints for managing delivery vehicles")
public class DeliveryVehicleController {

	private final DeliveryVehicleRepository deliveryVehicleRepository;

	@GetMapping
	@Operation(summary = "Get all delivery vehicles")
	public List<DeliveryVehicle> getAllDeliveryVehicles() {
		return deliveryVehicleRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get delivery vehicle by ID")
	public ResponseEntity<DeliveryVehicle> getDeliveryVehicleById(@PathVariable Long id) {
		DeliveryVehicle vehicle = deliveryVehicleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeliveryVehicle", id));
		return ResponseEntity.ok(vehicle);
	}

	@PostMapping
	@Operation(summary = "Create a new delivery vehicle")
	public ResponseEntity<DeliveryVehicle> createDeliveryVehicle(@RequestBody DeliveryVehicle deliveryVehicle) {
		DeliveryVehicle saved = deliveryVehicleRepository.save(deliveryVehicle);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a delivery vehicle")
	public ResponseEntity<DeliveryVehicle> updateDeliveryVehicle(@PathVariable Long id, @RequestBody DeliveryVehicle vehicleDetails) {
		DeliveryVehicle vehicle = deliveryVehicleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeliveryVehicle", id));

		vehicle.setBranchId(vehicleDetails.getBranchId());
		vehicle.setVehicleType(vehicleDetails.getVehicleType());
		vehicle.setCapacity(vehicleDetails.getCapacity());
		vehicle.setStatus(vehicleDetails.getStatus());

		DeliveryVehicle updated = deliveryVehicleRepository.save(vehicle);
		return ResponseEntity.ok(updated);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a delivery vehicle")
	public ResponseEntity<Void> deleteDeliveryVehicle(@PathVariable Long id) {
		DeliveryVehicle vehicle = deliveryVehicleRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("DeliveryVehicle", id));
		deliveryVehicleRepository.delete(vehicle);
		return ResponseEntity.noContent().build();
	}
}
