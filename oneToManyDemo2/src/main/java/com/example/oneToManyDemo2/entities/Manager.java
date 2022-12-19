package com.example.oneToManyDemo2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Manager {
	
	@Id
	private Integer mid;
	private String mname;

}
