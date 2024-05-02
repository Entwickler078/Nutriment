package com.HospitalManagementSystem.dto;

import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import lombok.Data;

@Data
public class ServiceItemSearchDto extends DataTablesInput {

	private String searchText;
}
