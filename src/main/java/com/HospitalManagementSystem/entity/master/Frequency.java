package com.HospitalManagementSystem.entity.master;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "frequency")
@Data
public class Frequency implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long frequencyId;

	private String valueStr;
	
	private Integer value;

	private Boolean isActive = Boolean.FALSE;
}
