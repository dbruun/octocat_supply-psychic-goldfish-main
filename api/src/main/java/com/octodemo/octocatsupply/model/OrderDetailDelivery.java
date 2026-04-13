package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_detail_deliveries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDelivery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_detail_delivery_id")
	private Long orderDetailDeliveryId;

	@Column(name = "order_detail_id", nullable = false)
	private Long orderDetailId;

	@Column(name = "delivery_id", nullable = false)
	private Long deliveryId;

	@Column(nullable = false)
	private Integer quantity;

	@Column
	private String notes;
}
