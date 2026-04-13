package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Order;
import com.octodemo.octocatsupply.repository.OrderRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "API endpoints for managing orders")
public class OrderController {

	private final OrderRepository orderRepository;

	@GetMapping
	@Operation(summary = "Get all orders")
	public List<Order> getAllOrders() {
		return orderRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get order by ID")
	public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order", id));
		return ResponseEntity.ok(order);
	}

	@PostMapping
	@Operation(summary = "Create a new order")
	public ResponseEntity<Order> createOrder(@RequestBody Order order) {
		Order savedOrder = orderRepository.save(order);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrder);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an order")
	public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestBody Order orderDetails) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order", id));

		order.setBranchId(orderDetails.getBranchId());
		order.setOrderDate(orderDetails.getOrderDate());
		order.setName(orderDetails.getName());
		order.setDescription(orderDetails.getDescription());
		order.setStatus(orderDetails.getStatus());

		Order updatedOrder = orderRepository.save(order);
		return ResponseEntity.ok(updatedOrder);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an order")
	public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
		Order order = orderRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Order", id));
		
		orderRepository.delete(order);
		return ResponseEntity.noContent().build();
	}
}
