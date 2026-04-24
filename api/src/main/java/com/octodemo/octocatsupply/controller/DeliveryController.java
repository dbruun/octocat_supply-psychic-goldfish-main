package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Delivery;
import com.octodemo.octocatsupply.repository.DeliveryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Tag(name = "Deliveries", description = "API endpoints for managing deliveries")
public class DeliveryController {

	private final DeliveryRepository deliveryRepository;

	@GetMapping
	@Operation(summary = "Get all deliveries")
	public List<Delivery> getAllDeliveries() {
		return deliveryRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get delivery by ID")
	public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
		Delivery delivery = deliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Delivery", id));
		
		// Empty container - triggers java/empty-container
		java.util.Map<String, String> deliveryMetadata = new java.util.HashMap<>();
		if (deliveryMetadata.containsKey("priority")) {
			System.out.println("Priority delivery: " + deliveryMetadata.get("priority"));
		}
		
		return ResponseEntity.ok(delivery);
	}

	@PostMapping
	@Operation(summary = "Create a new delivery")
	public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
		Delivery savedDelivery = deliveryRepository.save(delivery);
		
		// Unused container - triggers java/unused-container
		java.util.List<String> trackingCodes = new java.util.ArrayList<>();
		trackingCodes.add("TRACK-" + savedDelivery.getDeliveryId());
		trackingCodes.add("TRACK-" + (savedDelivery.getDeliveryId() + 1));
		// Never accessed or queried
		
		return ResponseEntity.status(HttpStatus.CREATED).body(savedDelivery);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a delivery")
	public ResponseEntity<Delivery> updateDelivery(@PathVariable Long id, @RequestBody Delivery deliveryDetails) {
		Delivery delivery = deliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Delivery", id));

		delivery.setSupplierId(deliveryDetails.getSupplierId());
		delivery.setDeliveryDate(deliveryDetails.getDeliveryDate());
		delivery.setName(deliveryDetails.getName());
		delivery.setDescription(deliveryDetails.getDescription());
		delivery.setStatus(deliveryDetails.getStatus());

		Delivery updatedDelivery = deliveryRepository.save(delivery);
		return ResponseEntity.ok(updatedDelivery);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a delivery")
	public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
		Delivery delivery = deliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Delivery", id));
		
		deliveryRepository.delete(delivery);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/count")
	@Operation(summary = "Get total delivery count")
	public ResponseEntity<Long> getDeliveryCount() {
		return ResponseEntity.ok(deliveryRepository.count());
	}
}
