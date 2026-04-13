package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_detail_id")
	private Long orderDetailId;

	@Column(name = "order_id", nullable = false)
	private Long orderId;

	@Column(name = "product_id", nullable = false)
	private Long productId;

	@Column(nullable = false)
	private Integer quantity;

	@Column(name = "unit_price", nullable = false)
	private Double unitPrice;

	@Column
	private String notes;
}
