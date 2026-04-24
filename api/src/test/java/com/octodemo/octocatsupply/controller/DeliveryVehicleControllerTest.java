package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.DeliveryVehicle;
import com.octodemo.octocatsupply.repository.DeliveryVehicleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeliveryVehicleControllerTest {

	@Mock
	private DeliveryVehicleRepository deliveryVehicleRepository;

	@InjectMocks
	private DeliveryVehicleController deliveryVehicleController;

	@Test
	void shouldReturnAllDeliveryVehicles() {
		DeliveryVehicle vehicle1 = new DeliveryVehicle(1L, 1L, "truck", 5000.0, "available");
		DeliveryVehicle vehicle2 = new DeliveryVehicle(2L, 1L, "van", 1500.0, "in-transit");

		when(deliveryVehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle1, vehicle2));

		List<DeliveryVehicle> result = deliveryVehicleController.getAllDeliveryVehicles();

		assertEquals(2, result.size());
		assertEquals("truck", result.get(0).getVehicleType());
		assertEquals("van", result.get(1).getVehicleType());
		verify(deliveryVehicleRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnEmptyListWhenNoDeliveryVehicles() {
		when(deliveryVehicleRepository.findAll()).thenReturn(List.of());

		List<DeliveryVehicle> result = deliveryVehicleController.getAllDeliveryVehicles();

		assertTrue(result.isEmpty());
		verify(deliveryVehicleRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnDeliveryVehicleById() {
		DeliveryVehicle vehicle = new DeliveryVehicle(1L, 1L, "truck", 5000.0, "available");

		when(deliveryVehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

		ResponseEntity<DeliveryVehicle> response = deliveryVehicleController.getDeliveryVehicleById(1L);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("truck", response.getBody().getVehicleType());
		assertEquals(5000.0, response.getBody().getCapacity());
		verify(deliveryVehicleRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowWhenDeliveryVehicleNotFound() {
		when(deliveryVehicleRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			deliveryVehicleController.getDeliveryVehicleById(999L);
		});

		verify(deliveryVehicleRepository, times(1)).findById(999L);
	}

	@Test
	void shouldCreateDeliveryVehicle() {
		DeliveryVehicle input = new DeliveryVehicle(null, 2L, "van", 2000.0, "available");
		DeliveryVehicle saved = new DeliveryVehicle(5L, 2L, "van", 2000.0, "available");

		when(deliveryVehicleRepository.save(input)).thenReturn(saved);

		ResponseEntity<DeliveryVehicle> response = deliveryVehicleController.createDeliveryVehicle(input);

		assertEquals(201, response.getStatusCode().value());
		assertNotNull(response.getBody());
		assertEquals(5L, response.getBody().getVehicleId());
		assertEquals("van", response.getBody().getVehicleType());
		verify(deliveryVehicleRepository, times(1)).save(input);
	}

	@Test
	void shouldUpdateDeliveryVehicle() {
		DeliveryVehicle existing = new DeliveryVehicle(1L, 1L, "truck", 5000.0, "available");
		DeliveryVehicle details = new DeliveryVehicle(null, 2L, "van", 1500.0, "maintenance");
		DeliveryVehicle updated = new DeliveryVehicle(1L, 2L, "van", 1500.0, "maintenance");

		when(deliveryVehicleRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(deliveryVehicleRepository.save(any(DeliveryVehicle.class))).thenReturn(updated);

		ResponseEntity<DeliveryVehicle> response = deliveryVehicleController.updateDeliveryVehicle(1L, details);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("van", response.getBody().getVehicleType());
		assertEquals("maintenance", response.getBody().getStatus());
		verify(deliveryVehicleRepository, times(1)).findById(1L);
		verify(deliveryVehicleRepository, times(1)).save(any(DeliveryVehicle.class));
	}

	@Test
	void shouldThrowWhenUpdatingNonExistentDeliveryVehicle() {
		DeliveryVehicle details = new DeliveryVehicle(null, 1L, "truck", 5000.0, "available");

		when(deliveryVehicleRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			deliveryVehicleController.updateDeliveryVehicle(999L, details);
		});

		verify(deliveryVehicleRepository, times(1)).findById(999L);
		verify(deliveryVehicleRepository, never()).save(any());
	}

	@Test
	void shouldDeleteDeliveryVehicle() {
		DeliveryVehicle vehicle = new DeliveryVehicle(1L, 1L, "truck", 5000.0, "available");

		when(deliveryVehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

		ResponseEntity<Void> response = deliveryVehicleController.deleteDeliveryVehicle(1L);

		assertEquals(204, response.getStatusCode().value());
		verify(deliveryVehicleRepository, times(1)).findById(1L);
		verify(deliveryVehicleRepository, times(1)).delete(vehicle);
	}

	@Test
	void shouldThrowWhenDeletingNonExistentDeliveryVehicle() {
		when(deliveryVehicleRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			deliveryVehicleController.deleteDeliveryVehicle(999L);
		});

		verify(deliveryVehicleRepository, times(1)).findById(999L);
		verify(deliveryVehicleRepository, never()).delete(any());
	}
}
