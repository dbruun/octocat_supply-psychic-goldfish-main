package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long deliveryId;

	@Column(name = "supplier_id", nullable = false)
	private Long supplierId;

	@Column(name = "delivery_date", nullable = false)
	private String deliveryDate;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column(nullable = false)
	private String status = "pending";
}
