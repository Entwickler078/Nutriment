package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.HospitalManagementSystem.entity.master.ServiceItems;

public interface ServiceItemsDataTablesRepository extends DataTablesRepository<ServiceItems, Long> {

	Integer countByIsActive(Boolean isActive);

}