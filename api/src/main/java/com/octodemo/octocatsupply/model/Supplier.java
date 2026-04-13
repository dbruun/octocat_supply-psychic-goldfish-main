package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "suppliers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supplier_id")
	private Long supplierId;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column
	private String email;

	@Column
	private String phone;

	@Column(nullable = false)
	private Boolean active = true;

	@Column(nullable = false)
	private Boolean verified = false;
}
