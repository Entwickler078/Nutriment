package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.history.AdHocItemsHistory;

public interface AdHocItemsHistoryRepository extends JpaRepository<AdHocItemsHistory, Long> {

}