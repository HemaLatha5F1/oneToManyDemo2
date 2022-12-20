package com.example.oneToManyDemo2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Investment {

	@Id
	private Integer id;
	private Double Salary;
	private String type;
	@Column(name = "customer_id")
	private Integer customerId;

}
