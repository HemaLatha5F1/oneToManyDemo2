package com.example.oneToManyDemo2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Customer {

	@Id
	private Integer cid;
	private String cname;
	// cip(risk profile 1 to 6)
	private String cip;
	@Column(name = "manager_id")
	private Integer managerId;

}
