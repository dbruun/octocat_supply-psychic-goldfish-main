package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Headquarters;
import com.octodemo.octocatsupply.repository.HeadquartersRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/headquarters")
@RequiredArgsConstructor
@Tag(name = "Headquarters", description = "API endpoints for managing headquarters")
public class HeadquartersController {

	private final HeadquartersRepository headquartersRepository;

	@GetMapping
	@Operation(summary = "Get all headquarters")
	public List<Headquarters> getAllHeadquarters() {
		return headquartersRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get headquarters by ID")
	public ResponseEntity<Headquarters> getHeadquartersById(@PathVariable Long id) {
		Headquarters headquarters = headquartersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Headquarters", id));
		return ResponseEntity.ok(headquarters);
	}

	@PostMapping
	@Operation(summary = "Create new headquarters")
	public ResponseEntity<Headquarters> createHeadquarters(@RequestBody Headquarters headquarters) {
		Headquarters savedHeadquarters = headquartersRepository.save(headquarters);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedHeadquarters);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update headquarters")
	public ResponseEntity<Headquarters> updateHeadquarters(@PathVariable Long id, @RequestBody Headquarters headquartersDetails) {
		Headquarters headquarters = headquartersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Headquarters", id));

		headquarters.setName(headquartersDetails.getName());
		headquarters.setDescription(headquartersDetails.getDescription());
		headquarters.setAddress(headquartersDetails.getAddress());
		headquarters.setContactPerson(headquartersDetails.getContactPerson());
		headquarters.setEmail(headquartersDetails.getEmail());
		headquarters.setPhone(headquartersDetails.getPhone());

		Headquarters updatedHeadquarters = headquartersRepository.save(headquarters);
		return ResponseEntity.ok(updatedHeadquarters);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete headquarters")
	public ResponseEntity<Void> deleteHeadquarters(@PathVariable Long id) {
		Headquarters headquarters = headquartersRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Headquarters", id));
		
		headquartersRepository.delete(headquarters);
		return ResponseEntity.noContent().build();
	}
}
