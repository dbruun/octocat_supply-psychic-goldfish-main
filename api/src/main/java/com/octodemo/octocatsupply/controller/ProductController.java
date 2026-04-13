package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.exception.ResourceNotFoundException;
import com.octodemo.octocatsupply.model.Product;
import com.octodemo.octocatsupply.repository.ProductRepository;
import com.octodemo.octocatsupply.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "API endpoints for managing products")
public class ProductController {

	private final ProductRepository productRepository;
	private final ProductService productService;

	@GetMapping
	@Operation(summary = "Get all products")
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get product by ID")
	public ResponseEntity<Product> getProductById(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", id));
		return ResponseEntity.ok(product);
	}

	@GetMapping("/name/{name}")
	@Operation(summary = "Search products by name (supports wildcards)")
	public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
		if (name.equals("")) {
			return ResponseEntity.badRequest().build();
		}
		
		List<Product> products = productService.searchProductsByName(name);
		if (products.isEmpty()) {
			throw new ResourceNotFoundException("Product with name: " + name);
		}
		return ResponseEntity.ok(products);
	}

	@PostMapping
	@Operation(summary = "Create a new product")
	public ResponseEntity<Product> createProduct(@RequestBody Product product) {
		Product savedProduct = productRepository.save(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a product")
	public ResponseEntity<Product> updateProduct(@PathVariable String id, @RequestBody Product productDetails) {
		Long productId = Long.parseLong(id);
		
		Product product = productRepository.findById(productId)
				.orElseThrow(() -> new ResourceNotFoundException("Product", productId));

		product.setName(productDetails.getName());
		product.setDescription(productDetails.getDescription());
		product.setPrice(productDetails.getPrice());
		product.setSku(productDetails.getSku());
		product.setUnit(productDetails.getUnit());
		product.setImgName(productDetails.getImgName());
		product.setDiscount(productDetails.getDiscount());
		product.setSupplierId(productDetails.getSupplierId());

		Product updatedProduct = productRepository.save(product);
		return ResponseEntity.ok(updatedProduct);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a product")
	public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
		Product product = productRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", id));
		
		productRepository.delete(product);
		return ResponseEntity.noContent().build();
	}
}
