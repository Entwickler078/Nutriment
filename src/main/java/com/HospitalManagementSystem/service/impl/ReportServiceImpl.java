package com.HospitalManagementSystem.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.HospitalManagementSystem.dto.PatientServiceReportDto;
import com.HospitalManagementSystem.dto.StickerServiceComorbidityReportDto;
import com.HospitalManagementSystem.dto.StickerServiceReportDto;
import com.HospitalManagementSystem.entity.DietPlan;
import com.HospitalManagementSystem.entity.Patient;
import com.HospitalManagementSystem.entity.master.Diagonosis;
import com.HospitalManagementSystem.repository.DiagonosisRepository;
import com.HospitalManagementSystem.repository.DietPlanRepository;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.DietTypeOralSolidRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.service.ReportService;
import com.HospitalManagementSystem.service.StickersService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.ReportServiceUtility;

@Service
@SuppressWarnings("unchecked")
public class ReportServiceImpl implements ReportService {

	@PersistenceContext
	EntityManager em;
	
	@Autowired
	private StickersService stickersService;

	@Autowired
	private DietTypeOralSolidRepository dietTypeOralSolidRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private DiagonosisRepository diagonosisRepository;
	@Autowired
	private DietPlanRepository dietPlanRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;

	@Override
	public String patientServiceReport(Model model) {
		List<Diagonosis> diagonosisList = diagonosisRepository.findAllByIsActive(Boolean.TRUE);
		Diagonosis otherDiagonosis = diagonosisList.get(0);
		diagonosisList.remove(0);
		diagonosisList.add(otherDiagonosis);
		model.addAttribute("localDateTimeFormatter", CommonUtility.localDateTimeFormatter);
		model.addAttribute("dietTypeOralSolidList", dietTypeOralSolidRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietSubTypeList", dietSubTypeRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("diagonosisList", diagonosisList);
		return "report/PatientServiceReport";
	}

	@Override
	public String patientServiceReport(Model model, Integer patientServiceReport, String dateSelection, String diagonosisIds, String dietTypeOralSolidIds, String dietSubTypeIds) {
		List<PatientServiceReportDto> patientServiceReportList = new ArrayList<>();
		String[] dateSelections = dateSelection.split(" - ");
		LocalDate startDate = LocalDate.parse(dateSelections[0], CommonUtility.localDateFormatter);
		LocalDate endDate = LocalDate.parse(dateSelections[1], CommonUtility.localDateFormatter);
		if (patientServiceReport == 1) {
			if (StringUtils.isNotEmpty(dietTypeOralSolidIds)) {
				List<String> dietTypeOralSolidIdsList = Stream.of(dietTypeOralSolidIds.split(",", -1)).collect(Collectors.toList());
				Boolean extraLiquid = dietTypeOralSolidIdsList.contains("0");
				dietTypeOralSolidIdsList.remove("0");
				
				if (CollectionUtils.isNotEmpty(dietTypeOralSolidIdsList)) {
					Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIET_TYPE);
					query.setParameter("startDate", startDate);
					query.setParameter("endDate", endDate);
					query.setParameter("dietTypeOralSolidIds", Stream.of(dietTypeOralSolidIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
					List<Object[]> list = query.getResultList();
					
					if (CollectionUtils.isNotEmpty(list)) {
						for (Object[] object : list) {
							PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
							patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
							patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
							patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
							patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
							patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
							patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
							patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
							patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
							patientServiceReportDto.setDietSubType("");
							patientServiceReportList.add(patientServiceReportDto);
						}
					}
				}
				
				if (extraLiquid) {
					Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_EXTRA_LIQUID);
					query.setParameter("startDate", startDate);
					query.setParameter("endDate", endDate);
					List<Object[]> list = query.getResultList();
					
					if (CollectionUtils.isNotEmpty(list)) {
						for (Object[] object : list) {
							PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
							patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
							patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
							patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
							patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
							patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
							patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
							patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
							patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
							patientServiceReportDto.setDietSubType("");
							patientServiceReportList.add(patientServiceReportDto);
						}
					}
				}
				
			}
			
			if (StringUtils.isNotEmpty(dietSubTypeIds)) { 
				Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIET_SUB_TYPE);
				query.setParameter("startDate", startDate);
				query.setParameter("endDate", endDate);
				query.setParameter("dietSubTypeIds", Stream.of(dietSubTypeIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
				List<Object[]> list = query.getResultList();
				
				if (CollectionUtils.isNotEmpty(list)) {
					for (Object[] object : list) {
						PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
						patientServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
						patientServiceReportDto.setDietSubType(ReportServiceUtility.getString(object[1]));
						patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[2]));
						patientServiceReportDto.setDd(ReportServiceUtility.getString(object[3]));
						patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[4]));
						patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[5]));
						patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[6]));
						patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[7]));
						patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[8]));
						patientServiceReportList.add(patientServiceReportDto);
					}
				}
			}
		} else if (patientServiceReport == 2) {
			Query query = em.createNativeQuery(ReportServiceUtility.PATIENT_SERVICE_REPORT_DIAGONOSIS);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			query.setParameter("diagonosisIds", Stream.of(diagonosisIds.split(",", -1)).map(x -> Long.valueOf(x)).collect(Collectors.toList()));
			List<Object[]> list = query.getResultList();
			
			if (CollectionUtils.isNotEmpty(list)) {
				for (Object[] object : list) {
					PatientServiceReportDto patientServiceReportDto = new PatientServiceReportDto();
					patientServiceReportDto.setDiagnosis(ReportServiceUtility.getString(object[0]));
					patientServiceReportDto.setNormal(ReportServiceUtility.getString(object[1]));
					patientServiceReportDto.setDd(ReportServiceUtility.getString(object[2]));
					patientServiceReportDto.setRenal(ReportServiceUtility.getString(object[3]));
					patientServiceReportDto.setSrd(ReportServiceUtility.getString(object[4]));
					patientServiceReportDto.setSfd(ReportServiceUtility.getString(object[5]));
					patientServiceReportDto.setFfd(ReportServiceUtility.getString(object[6]));
					patientServiceReportDto.setTotal(ReportServiceUtility.getString(object[7]));
					patientServiceReportDto.setDietType("");
					patientServiceReportDto.setDietSubType("");
					patientServiceReportList.add(patientServiceReportDto);
				}
			}
		}

		model.addAttribute("patientServiceReportList", patientServiceReportList);
		model.addAttribute("patientServiceReport", patientServiceReport);
		return patientServiceReport(model);
	}

	@Override
	public String stickerServiceReport(Model model, String dateSelection, Long serviceMasterId) {
		List<StickerServiceReportDto> stickerServiceReportList = new ArrayList<>();
		boolean showItemName = stickersService.isDietTypeLiquidOralTF(serviceMasterId);
		Query query = em.createNativeQuery(showItemName ? ReportServiceUtility.STICKER_SERVICE_REPORT_1 : ReportServiceUtility.STICKER_SERVICE_REPORT_2);
		query.setParameter("dateSelection", LocalDate.parse(dateSelection, CommonUtility.localDateFormatter));
		query.setParameter("serviceMasterId", serviceMasterId);
		List<Object[]> list = query.getResultList();

		if (CollectionUtils.isNotEmpty(list)) {
			for (Object[] object : list) {
				StickerServiceReportDto stickerServiceReportDto = new StickerServiceReportDto();
				stickerServiceReportDto.setDietType(ReportServiceUtility.getString(object[0]));
				stickerServiceReportDto.setDietSubType(ReportServiceUtility.getString(object[1]));
				stickerServiceReportDto.setItemName(ReportServiceUtility.getString(object[2]));
				stickerServiceReportDto.setTotal(ReportServiceUtility.getString(object[3]));
				stickerServiceReportList.add(stickerServiceReportDto);
			}
		}
		model.addAttribute("stickerServiceReportList", stickerServiceReportList);
		model.addAttribute("reportGenerated", true);
		model.addAttribute("dateSelection", dateSelection);
		model.addAttribute("serviceMasterId", serviceMasterId);
		model.addAttribute("serviceMaster", serviceMasterRepository.getByServiceMasterId(serviceMasterId));
		model.addAttribute("showItemName", showItemName);
		return stickersService.stickers(model, null);
	}

	@Override
	public String stickerServiceComorbidityReport(Model model, String dateSelection, Long serviceMasterId, String itemName) {
		List<StickerServiceComorbidityReportDto> stickerServiceComorbidityReportList = new ArrayList<>();
		Map<String, StickerServiceComorbidityReportDto> finalDataMap = new HashMap<>();
		List<DietPlan> dietPlanList = new ArrayList<>();
		LocalDate dietDate = LocalDate.parse(dateSelection, CommonUtility.localDateFormatter);
		int total = 0;
		if (StringUtils.isNotEmpty(itemName)) {
			dietPlanList = dietPlanRepository.findAllByServiceMasterServiceMasterIdAndDietDateAndItem(serviceMasterId, dietDate, itemName);
		} else {
			dietPlanList = dietPlanRepository.findAllByServiceMasterServiceMasterIdAndDietDate(serviceMasterId, dietDate);
		}

		if (CollectionUtils.isNotEmpty(dietPlanList)) {
			for (DietPlan dietPlan : dietPlanList) {
				if (dietPlan.getIsPaused()) {
					continue;
				}
				Patient patient = dietPlan.getPatient();
				String medicalComorbiditiesString = patient.getMedicalComorbiditiesString(" + ");
				String patientInfo = dietPlan.getPatient().getPatientName() + "|" + dietPlan.getPatient().getBed().getBedCode();
				StickerServiceComorbidityReportDto stickerServiceComorbidityReportDto = null;
				if (finalDataMap.containsKey(medicalComorbiditiesString)) {
					stickerServiceComorbidityReportDto = finalDataMap.get(medicalComorbiditiesString);
				} else {
					stickerServiceComorbidityReportDto = new StickerServiceComorbidityReportDto();
					stickerServiceComorbidityReportDto.setComorbidityValue(medicalComorbiditiesString);
					finalDataMap.put(medicalComorbiditiesString, stickerServiceComorbidityReportDto);
					stickerServiceComorbidityReportList.add(stickerServiceComorbidityReportDto);
				}
				stickerServiceComorbidityReportDto.setTotal(stickerServiceComorbidityReportDto.getTotal() + 1);
				total++;
				if (CollectionUtils.isNotEmpty(dietPlan.getDietInstructions())) {
					stickerServiceComorbidityReportDto.getDietInstructions().add(patientInfo + "  :  " + dietPlan.getDietInstructions().stream().map(x -> String.valueOf(x.getInstruction())).collect(Collectors.joining(" | ")));
				}
				if (StringUtils.isNotEmpty(patient.getSpecialNotesByNursingString())) {
					stickerServiceComorbidityReportDto.getNursingInstructions().add(patientInfo + "  :  " + patient.getSpecialNotesByNursingString(" | "));
				}
			}
			StickerServiceComorbidityReportDto stickerServiceComorbidityReportDto = new StickerServiceComorbidityReportDto();
			stickerServiceComorbidityReportDto.setComorbidityValue("Total");
			stickerServiceComorbidityReportDto.setTotal(total);
			stickerServiceComorbidityReportList.add(stickerServiceComorbidityReportDto);
		}
		model.addAttribute("stickerServiceComorbidityReportList", stickerServiceComorbidityReportList);
		model.addAttribute("dateSelection", dateSelection);
		model.addAttribute("serviceMaster", serviceMasterRepository.getByServiceMasterId(serviceMasterId));
		model.addAttribute("itemName", itemName);
		return "report/StickerServiceComorbidityReport";
	}

}
