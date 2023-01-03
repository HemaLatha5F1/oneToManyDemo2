package com.example.oneToManyDemo2.entities;

import java.math.BigDecimal;

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
	@Column(name = "investment_id")
	private Integer investmentId;
	@Column(name = "total_assert_value")
	private BigDecimal totalAssertValue;
	@Column(name = "assert_type")
	private String assertType;
	@Column(name = "customer_id")
	private Integer customerId;

}
