package com.HospitalManagementSystem.service;

import org.springframework.ui.Model;

public interface ReportService {

	String patientServiceReport(Model model);

	String patientServiceReport(Model model, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds);

	String stickerServiceReport(Model model, String dateSelection, Long serviceMasterId);

	String stickerServiceComorbidityReport(Model model, String dateSelection, Long serviceMasterId, String itemName);

}
