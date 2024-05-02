package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;

import com.HospitalManagementSystem.entity.master.AdHocItems;

public interface AdHocItemsDataTablesRepository extends DataTablesRepository<AdHocItems, Long> {

	Integer countByIsActive(Boolean isActive);

}