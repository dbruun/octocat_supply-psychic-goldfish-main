package com.octodemo.octocatsupply.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "headquarters")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Headquarters {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "headquarters_id")
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
