package com.HospitalManagementSystem.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.HospitalManagementSystem.dto.DietPlanSearchDto;
import com.HospitalManagementSystem.dto.PatientDataTablesOutputDto;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.master.ServiceItems;

public interface DietPlanService {

	void prepareDietPlan(List<Patient> patientList, User currentUser, Boolean prepareAll);

	PatientDataTablesOutputDto getDietPlanData(DietPlanSearchDto dietPlanSearchDto);

	ResponseEntity<String> updateDietPlanItem(Long dietPlanId, String item);

	void prepareDietPlan();

	ResponseEntity<String> updateDietPlanPausedUnpaused(Long dietPlanId, boolean isPaused);

	void updateDietInstruction(Patient patient);

	void deleteDietPlanForServiceItem(ServiceItems serviceItems, LocalDateTime now);

	void addDietPlanForServiceItem(ServiceItems saveServiceItems, LocalDateTime now, User currentUser);

}
