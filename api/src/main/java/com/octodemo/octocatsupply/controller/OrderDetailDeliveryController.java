package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.OrderDetailDelivery;
import com.octodemo.octocatsupply.repository.OrderDetailDeliveryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-detail-deliveries")
@RequiredArgsConstructor
@Tag(name = "Order Detail Deliveries", description = "API endpoints for managing order detail deliveries")
public class OrderDetailDeliveryController {

	private final OrderDetailDeliveryRepository orderDetailDeliveryRepository;

	@GetMapping
	@Operation(summary = "Get all order detail deliveries")
	public List<OrderDetailDelivery> getAllOrderDetailDeliveries() {
		return orderDetailDeliveryRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get order detail delivery by ID")
	public ResponseEntity<OrderDetailDelivery> getOrderDetailDeliveryById(@PathVariable Long id) {
		OrderDetailDelivery orderDetailDelivery = orderDetailDeliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetailDelivery", id));
		return ResponseEntity.ok(orderDetailDelivery);
	}

	@PostMapping
	@Operation(summary = "Create a new order detail delivery")
	public ResponseEntity<OrderDetailDelivery> createOrderDetailDelivery(@RequestBody OrderDetailDelivery orderDetailDelivery) {
		OrderDetailDelivery savedOrderDetailDelivery = orderDetailDeliveryRepository.save(orderDetailDelivery);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetailDelivery);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an order detail delivery")
	public ResponseEntity<OrderDetailDelivery> updateOrderDetailDelivery(@PathVariable Long id, @RequestBody OrderDetailDelivery orderDetailDeliveryDetails) {
		OrderDetailDelivery orderDetailDelivery = orderDetailDeliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetailDelivery", id));

		orderDetailDelivery.setOrderDetailId(orderDetailDeliveryDetails.getOrderDetailId());
		orderDetailDelivery.setDeliveryId(orderDetailDeliveryDetails.getDeliveryId());
		orderDetailDelivery.setQuantity(orderDetailDeliveryDetails.getQuantity());
		orderDetailDelivery.setNotes(orderDetailDeliveryDetails.getNotes());

		OrderDetailDelivery updatedOrderDetailDelivery = orderDetailDeliveryRepository.save(orderDetailDelivery);
		return ResponseEntity.ok(updatedOrderDetailDelivery);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an order detail delivery")
	public ResponseEntity<Void> deleteOrderDetailDelivery(@PathVariable Long id) {
		OrderDetailDelivery orderDetailDelivery = orderDetailDeliveryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetailDelivery", id));
		
		orderDetailDeliveryRepository.delete(orderDetailDelivery);
		return ResponseEntity.noContent().build();
	}
}
