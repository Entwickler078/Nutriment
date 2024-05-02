package com.HospitalManagementSystem.dto;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.HospitalManagementSystem.entity.master.ServiceItems;

import lombok.Data;

@Data
public class ServiceItemsDataTablesOutputDto {

	private DataTablesOutput<ServiceItems> data;

	private Integer count;

}
