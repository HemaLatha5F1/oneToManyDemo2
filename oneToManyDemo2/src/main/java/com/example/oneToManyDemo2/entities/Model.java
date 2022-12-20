package com.example.oneToManyDemo2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table
public class Model {
	
	@Id
	private Integer cip;
	private String assetName;
	private Integer assetPercentage;

}
