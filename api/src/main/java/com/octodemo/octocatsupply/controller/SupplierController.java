package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Supplier;
import com.octodemo.octocatsupply.repository.SupplierRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@Tag(name = "Suppliers", description = "API endpoints for managing suppliers")
public class SupplierController {

	private final SupplierRepository supplierRepository;

	@GetMapping
	@Operation(summary = "Get all suppliers")
	public List<Supplier> getAllSuppliers() {
		return supplierRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get supplier by ID")
	public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
		
		return ResponseEntity.ok(supplier);
	}

	@PostMapping
	@Operation(summary = "Create a new supplier")
	public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
		Supplier savedSupplier = supplierRepository.save(supplier);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedSupplier);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a supplier")
	public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @RequestBody Supplier supplierDetails) {
		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier", id));

		if (supplier != null) {
			supplier.setName(supplierDetails.getName());
			supplier.setDescription(supplierDetails.getDescription());
			supplier.setContactPerson(supplierDetails.getContactPerson());
			supplier.setEmail(supplierDetails.getEmail());
			supplier.setPhone(supplierDetails.getPhone());
			supplier.setActive(supplierDetails.getActive());
			supplier.setVerified(supplierDetails.getVerified());

			Supplier updatedSupplier = supplierRepository.save(supplier);
			return ResponseEntity.ok(updatedSupplier);
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a supplier")
	public ResponseEntity<Void> deleteSupplier(@PathVariable Long id) {
		Supplier supplier = supplierRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Supplier", id));
		
		supplierRepository.delete(supplier);
		return ResponseEntity.noContent().build();
	}
}
