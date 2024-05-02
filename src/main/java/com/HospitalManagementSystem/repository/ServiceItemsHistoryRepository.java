package com.HospitalManagementSystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.HospitalManagementSystem.entity.history.ServiceItemsHistory;

public interface ServiceItemsHistoryRepository extends JpaRepository<ServiceItemsHistory, Long> {

}