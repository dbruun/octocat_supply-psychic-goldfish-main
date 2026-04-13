package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "branches")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "branch_id")
	private Long branchId;

	@Column(name = "headquarters_id", nullable = false)
	private Long headquartersId;

	@Column(nullable = false)
	private String name;

	@Column
	private String description;

	@Column
	private String address;

	@Column(name = "contact_person")
	private String contactPerson;

	@Column
	private String email;

	@Column
	private String phone;
}
