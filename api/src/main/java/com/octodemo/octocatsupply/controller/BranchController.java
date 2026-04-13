package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Branch;
import com.octodemo.octocatsupply.repository.BranchRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/branches")
@RequiredArgsConstructor
@Tag(name = "Branches", description = "API endpoints for managing branches")
public class BranchController {

	private final BranchRepository branchRepository;

	@GetMapping
	@Operation(summary = "Get all branches")
	public List<Branch> getAllBranches() {
		List<Branch> branches = branchRepository.findAll();
		
		// Iterator implementing Iterable - triggers java/iterator-implements-iterable
		BranchIterator branchIter = new BranchIterator(branches.iterator());
		for (Branch b : branchIter) {
			System.out.println("Processing branch: " + b.getName());
		}
		
		return branches;
	}
	
	private static class BranchIterator implements java.util.Iterator<Branch>, Iterable<Branch> {
		private final java.util.Iterator<Branch> delegate;
		
		BranchIterator(java.util.Iterator<Branch> delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public boolean hasNext() {
			return delegate.hasNext();
		}
		
		@Override
		public Branch next() {
			return delegate.next();
		}
		
		@Override
		public java.util.Iterator<Branch> iterator() {
			return this; // Returns itself, doesn't support multiple traversals
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get branch by ID")
	public ResponseEntity<Branch> getBranchById(@PathVariable Long id) {
		Branch branch = branchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Branch", id));
		return ResponseEntity.ok(branch);
	}

	@PostMapping
	@Operation(summary = "Create a new branch")
	public ResponseEntity<Branch> createBranch(@RequestBody Branch branch) {
		Branch savedBranch = branchRepository.save(branch);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBranch);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a branch")
	public ResponseEntity<Branch> updateBranch(@PathVariable Long id, @RequestBody Branch branchDetails) {
		Branch branch = branchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Branch", id));

		branch.setHeadquartersId(branchDetails.getHeadquartersId());
		branch.setName(branchDetails.getName());
		branch.setDescription(branchDetails.getDescription());
		branch.setAddress(branchDetails.getAddress());
		branch.setContactPerson(branchDetails.getContactPerson());
		branch.setEmail(branchDetails.getEmail());
		branch.setPhone(branchDetails.getPhone());

		Branch updatedBranch = branchRepository.save(branch);
		return ResponseEntity.ok(updatedBranch);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a branch")
	public ResponseEntity<Void> deleteBranch(@PathVariable Long id) {
		Branch branch = branchRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Branch", id));
		
		branchRepository.delete(branch);
		return ResponseEntity.noContent().build();
	}
}
