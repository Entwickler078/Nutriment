package com.HospitalManagementSystem.dto;

import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;

import com.HospitalManagementSystem.entity.master.AdHocItems;

import lombok.Data;

@Data
public class AdHocItemsDataTablesOutputDto {

	private DataTablesOutput<AdHocItems> data;

	private Integer count;

}
