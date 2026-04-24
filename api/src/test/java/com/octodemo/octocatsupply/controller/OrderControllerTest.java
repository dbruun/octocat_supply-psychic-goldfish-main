package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Order;
import com.octodemo.octocatsupply.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

	@Mock
	private OrderRepository orderRepository;

	@InjectMocks
	private OrderController orderController;

	private Order createOrder(Long id, String name) {
		return new Order(id, 1L, "2025-01-15", name, "Description for " + name, "pending");
	}

	// ── GET /api/orders ─────────────────────────────────────────────────

	@Test
	void shouldReturnAllOrders() {
		Order o1 = createOrder(1L, "Order Alpha");
		Order o2 = createOrder(2L, "Order Beta");

		when(orderRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

		List<Order> orders = orderController.getAllOrders();

		assertEquals(2, orders.size());
		assertEquals("Order Alpha", orders.get(0).getName());
		assertEquals("Order Beta", orders.get(1).getName());
		verify(orderRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnEmptyListWhenNoOrders() {
		when(orderRepository.findAll()).thenReturn(Collections.emptyList());

		List<Order> orders = orderController.getAllOrders();

		assertTrue(orders.isEmpty());
		verify(orderRepository, times(1)).findAll();
	}

	// ── GET /api/orders/{id} ────────────────────────────────────────────

	@Test
	void shouldReturnOrderById() {
		Order order = createOrder(1L, "Order Alpha");

		when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

		ResponseEntity<Order> response = orderController.getOrderById(1L);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("Order Alpha", response.getBody().getName());
		assertEquals(1L, response.getBody().getBranchId());
		assertEquals("pending", response.getBody().getStatus());
		verify(orderRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowWhenOrderNotFound() {
		when(orderRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			orderController.getOrderById(999L);
		});

		verify(orderRepository, times(1)).findById(999L);
	}

	// ── POST /api/orders ────────────────────────────────────────────────

	@Test
	void shouldCreateOrder() {
		Order input = new Order(null, 2L, "2025-03-01", "New Order", "Rush delivery", "pending");
		Order saved = new Order(10L, 2L, "2025-03-01", "New Order", "Rush delivery", "pending");

		when(orderRepository.save(any(Order.class))).thenReturn(saved);

		ResponseEntity<Order> response = orderController.createOrder(input);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(10L, response.getBody().getOrderId());
		assertEquals("New Order", response.getBody().getName());
		verify(orderRepository, times(1)).save(input);
	}

	// ── PUT /api/orders/{id} ────────────────────────────────────────────

	@Test
	void shouldUpdateOrder() {
		Order existing = createOrder(1L, "Old Name");
		Order details = new Order(null, 3L, "2025-06-01", "Updated Name", "Updated desc", "shipped");
		Order updated = new Order(1L, 3L, "2025-06-01", "Updated Name", "Updated desc", "shipped");

		when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(orderRepository.save(any(Order.class))).thenReturn(updated);

		ResponseEntity<Order> response = orderController.updateOrder(1L, details);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("Updated Name", response.getBody().getName());
		assertEquals("shipped", response.getBody().getStatus());
		assertEquals(3L, response.getBody().getBranchId());
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepository, times(1)).save(existing);
	}

	@Test
	void shouldThrowWhenUpdatingNonExistentOrder() {
		Order details = createOrder(null, "Whatever");

		when(orderRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			orderController.updateOrder(999L, details);
		});

		verify(orderRepository, times(1)).findById(999L);
		verify(orderRepository, never()).save(any());
	}

	// ── DELETE /api/orders/{id} ─────────────────────────────────────────

	@Test
	void shouldDeleteOrder() {
		Order existing = createOrder(1L, "Order Alpha");

		when(orderRepository.findById(1L)).thenReturn(Optional.of(existing));
		doNothing().when(orderRepository).delete(existing);

		ResponseEntity<Void> response = orderController.deleteOrder(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(orderRepository, times(1)).findById(1L);
		verify(orderRepository, times(1)).delete(existing);
	}

	@Test
	void shouldThrowWhenDeletingNonExistentOrder() {
		when(orderRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			orderController.deleteOrder(999L);
		});

		verify(orderRepository, times(1)).findById(999L);
		verify(orderRepository, never()).delete(any());
	}
}
