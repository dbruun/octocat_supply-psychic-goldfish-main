package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.OrderDetail;
import com.octodemo.octocatsupply.repository.OrderDetailRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-details")
@RequiredArgsConstructor
@Tag(name = "Order Details", description = "API endpoints for managing order details")
public class OrderDetailController {

	private final OrderDetailRepository orderDetailRepository;

	@GetMapping
	@Operation(summary = "Get all order details")
	public List<OrderDetail> getAllOrderDetails() {
		return orderDetailRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get order detail by ID")
	public ResponseEntity<OrderDetail> getOrderDetailById(@PathVariable Long id) {
		OrderDetail orderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetail", id));
		return ResponseEntity.ok(orderDetail);
	}

	@PostMapping
	@Operation(summary = "Create a new order detail")
	public ResponseEntity<OrderDetail> createOrderDetail(@RequestBody OrderDetail orderDetail) {
		OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedOrderDetail);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update an order detail")
	public ResponseEntity<OrderDetail> updateOrderDetail(@PathVariable Long id, @RequestBody OrderDetail orderDetailDetails) {
		OrderDetail orderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetail", id));

		orderDetail.setOrderId(orderDetailDetails.getOrderId());
		orderDetail.setProductId(orderDetailDetails.getProductId());
		orderDetail.setQuantity(orderDetailDetails.getQuantity());
		orderDetail.setUnitPrice(orderDetailDetails.getUnitPrice());
		orderDetail.setNotes(orderDetailDetails.getNotes());

		OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
		return ResponseEntity.ok(updatedOrderDetail);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete an order detail")
	public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
		OrderDetail orderDetail = orderDetailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("OrderDetail", id));
		
		orderDetailRepository.delete(orderDetail);
		return ResponseEntity.noContent().build();
	}
}
