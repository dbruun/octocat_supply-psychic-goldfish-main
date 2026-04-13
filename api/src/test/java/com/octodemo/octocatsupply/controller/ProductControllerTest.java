package com.octodemo.octocatsupply.controller;

import com.octodemo.octocatsupply.model.Product;
import com.octodemo.octocatsupply.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
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
class ProductControllerTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductController productController;

	@Test
	void shouldReturnAllProducts() {
		Product product1 = new Product(1L, 1L, "Product 1", "Description 1", 99.99, "SKU-001", "piece", "img1.png", 0.0);
		Product product2 = new Product(2L, 1L, "Product 2", "Description 2", 149.99, "SKU-002", "piece", "img2.png", 0.1);

		when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

		List<Product> products = productController.getAllProducts();

		assertEquals(2, products.size());
		assertEquals("Product 1", products.get(0).getName());
		assertEquals("Product 2", products.get(1).getName());
		verify(productRepository, times(1)).findAll();
	}

	@Test
	void shouldReturnProductById() {
		Product product = new Product(1L, 1L, "Product 1", "Description 1", 99.99, "SKU-001", "piece", "img1.png", 0.0);

		when(productRepository.findById(1L)).thenReturn(Optional.of(product));

		ResponseEntity<Product> response = productController.getProductById(1L);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertNotNull(response.getBody());
		assertEquals("Product 1", response.getBody().getName());
		assertEquals(99.99, response.getBody().getPrice());
		verify(productRepository, times(1)).findById(1L);
	}

	@Test
	void shouldReturn404WhenProductNotFound() {
		when(productRepository.findById(999L)).thenReturn(Optional.empty());

		assertThrows(com.octodemo.octocatsupply.exception.ResourceNotFoundException.class, () -> {
			productController.getProductById(999L);
		});

		verify(productRepository, times(1)).findById(999L);
	}
}
