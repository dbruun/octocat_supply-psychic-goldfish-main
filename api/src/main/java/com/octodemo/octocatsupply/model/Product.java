package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "supplier_id", nullable = false)
	private Long supplierId;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private String sku;

	@Column(nullable = false)
	private String unit;

	@Column(name = "img_name")
	private String imgName;

	@Column
	private Double discount;
}
