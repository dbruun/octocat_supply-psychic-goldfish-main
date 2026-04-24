package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Supplier;
import com.octodemo.octocatsupply.repository.SupplierRepository;
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
class SupplierControllerTest {

	@Mock
	private SupplierRepository supplierRepository;

	@InjectMocks
	private SupplierController supplierController;

	private Supplier createSupplier(Long id, String name) {
		return new Supplier(id, name, "Description for " + name, "Contact Person", name.toLowerCase() + "@example.com", "555-0000", true, false);
	}

	// ── GET /api/suppliers ──────────────────────────────────────────────

	@Test
	void shouldReturnAllSuppliers() {
		Supplier s1 = createSupplier(1L, "Acme Corp");
		Supplier s2 = createSupplier(2L, "Globex Inc");

		when(supplierRepository.findAll()).thenReturn(Arrays.asList(s1, s2));

		List<Supplier> suppliers = supplierController.getAllSuppliers();

		assertEquals(2, suppliers.size());
		assertEquals("Acme Corp", suppliers.get(0).getName());
		assertEquals("Globex Inc", suppliers.get(1).getName());
		verify(supplierRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnEmptyListWhenNoSuppliers() {
		when(supplierRepository.findAll()).thenReturn(Collections.emptyList());

		List<Supplier> suppliers = supplierController.getAllSuppliers();

		assertTrue(suppliers.isEmpty());
		verify(supplierRepository, times(1)).findAll();
	}

	// ── GET /api/suppliers/{id} ─────────────────────────────────────────

	@Test
	void shouldReturnSupplierById() {
		Supplier supplier = createSupplier(1L, "Acme Corp");

		when(supplierRepository.findById(1L)).thenReturn(Optional.of(supplier));

		ResponseEntity<Supplier> response = supplierController.getSupplierById(1L);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("Acme Corp", response.getBody().getName());
		assertEquals("acme corp@example.com", response.getBody().getEmail());
		verify(supplierRepository, times(1)).findById(1L);
	}

	@Test
	void shouldThrowWhenSupplierNotFound() {
		when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			supplierController.getSupplierById(999L);
		});

		verify(supplierRepository, times(1)).findById(999L);
	}

	// ── POST /api/suppliers ─────────────────────────────────────────────

	@Test
	void shouldCreateSupplier() {
		Supplier input = new Supplier(null, "New Supplier", "New desc", "Jane Doe", "jane@example.com", "555-1234", true, false);
		Supplier saved = new Supplier(10L, "New Supplier", "New desc", "Jane Doe", "jane@example.com", "555-1234", true, false);

		when(supplierRepository.save(any(Supplier.class))).thenReturn(saved);

		ResponseEntity<Supplier> response = supplierController.createSupplier(input);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertEquals(10L, response.getBody().getSupplierId());
		assertEquals("New Supplier", response.getBody().getName());
		verify(supplierRepository, times(1)).save(input);
	}

	// ── PUT /api/suppliers/{id} ─────────────────────────────────────────

	@Test
	void shouldUpdateSupplier() {
		Supplier existing = createSupplier(1L, "Old Name");
		Supplier details = new Supplier(null, "Updated Name", "Updated desc", "New Contact", "new@example.com", "555-9999", false, true);
		Supplier updated = new Supplier(1L, "Updated Name", "Updated desc", "New Contact", "new@example.com", "555-9999", false, true);

		when(supplierRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(supplierRepository.save(any(Supplier.class))).thenReturn(updated);

		ResponseEntity<Supplier> response = supplierController.updateSupplier(1L, details);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("Updated Name", response.getBody().getName());
		assertEquals("new@example.com", response.getBody().getEmail());
		assertFalse(response.getBody().getActive());
		assertTrue(response.getBody().getVerified());
		verify(supplierRepository, times(1)).findById(1L);
		verify(supplierRepository, times(1)).save(existing);
	}

	@Test
	void shouldThrowWhenUpdatingNonExistentSupplier() {
		Supplier details = createSupplier(null, "Whatever");

		when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			supplierController.updateSupplier(999L, details);
		});

		verify(supplierRepository, times(1)).findById(999L);
		verify(supplierRepository, never()).save(any());
	}

	// ── DELETE /api/suppliers/{id} ──────────────────────────────────────

	@Test
	void shouldDeleteSupplier() {
		Supplier existing = createSupplier(1L, "Acme Corp");

		when(supplierRepository.findById(1L)).thenReturn(Optional.of(existing));
		doNothing().when(supplierRepository).delete(existing);

		ResponseEntity<Void> response = supplierController.deleteSupplier(1L);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
		assertNull(response.getBody());
		verify(supplierRepository, times(1)).findById(1L);
		verify(supplierRepository, times(1)).delete(existing);
	}

	@Test
	void shouldThrowWhenDeletingNonExistentSupplier() {
		when(supplierRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(ResourceNotFoundException.class, () -> {
			supplierController.deleteSupplier(999L);
		});

		verify(supplierRepository, times(1)).findById(999L);
		verify(supplierRepository, never()).delete(any());
	}
}
