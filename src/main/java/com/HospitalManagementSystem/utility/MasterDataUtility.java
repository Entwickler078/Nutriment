package com.HospitalManagementSystem.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.HospitalManagementSystem.dto.DietTypesColumnDto;
import com.HospitalManagementSystem.entity.master.AdHocItems;
import com.HospitalManagementSystem.entity.master.DietSubType;
import com.HospitalManagementSystem.entity.master.DietTypeOralSolid;
import com.HospitalManagementSystem.entity.master.MedicalComorbidities;
import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.entity.master.ServiceMaster;
import com.HospitalManagementSystem.enums.YesNo;
import com.HospitalManagementSystem.repository.DietSubTypeRepository;
import com.HospitalManagementSystem.repository.DietTypeOralSolidRepository;
import com.HospitalManagementSystem.repository.MedicalComorbiditiesRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;

@Component
public class MasterDataUtility {

	public static Map<String, MedicalComorbidities> medicalComorbiditiesMap = new HashMap<>();
	public static Map<String, DietTypeOralSolid> dietTypeOralSolidMap = new HashMap<>();
	public static Map<String, DietSubType> dietSubTypeMap = new HashMap<>();
	public static Map<String, ServiceMaster> serviceMasterMap = new HashMap<>();

	@Autowired
	private MedicalComorbiditiesRepository medicalComorbiditiesRepository;
	@Autowired
	private DietTypeOralSolidRepository dietTypeOralSolidRepository;
	@Autowired
	private DietSubTypeRepository dietSubTypeRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;

	@PostConstruct
	public void setData() {
		List<MedicalComorbidities> medicalComorbiditiesList = medicalComorbiditiesRepository.findAll();
		// 1 Normal
		// 2 Diabetic Diet (DD)
		// 3 Renal
		// 4 Hypertensive (Salt Restricted-SRD)
		// 5 Salt Free
		// 6 FAT Free
		if (CollectionUtils.isNotEmpty(medicalComorbiditiesList)) {
			for (MedicalComorbidities medicalComorbidities : medicalComorbiditiesList) {
				medicalComorbiditiesMap.put(medicalComorbidities.getColumnName(), medicalComorbidities);
			}
		}

		List<DietTypeOralSolid> dietTypeOralSolidList = dietTypeOralSolidRepository.findAll();
		// 1 Full Diet
		// 2 Soft Diet
		// 3 Semi Solid Diet
		if (CollectionUtils.isNotEmpty(dietTypeOralSolidList)) {
			for (DietTypeOralSolid dietTypeOralSolid : dietTypeOralSolidList) {
				dietTypeOralSolidMap.put(dietTypeOralSolid.getColumnName(), dietTypeOralSolid);
			}
		}

		List<DietSubType> dietSubTypeList = dietSubTypeRepository.findAll();
		// 1 CLEAR LIQUIDS
		// 2 ALL LIQUIDS ORALLY
		// 3 BARIATRICS
		// 4 RT FEEDING
		// 5 PEG FEEDING
		// 6 NG FEEDING
		// 7 JJ FEEDING
		// 8 CLEAR LIQUIDS THROUGH TUBE FEEDING
		if (CollectionUtils.isNotEmpty(dietSubTypeList)) {
			for (DietSubType dietSubType : dietSubTypeList) {
				dietSubTypeMap.put(dietSubType.getColumnName(), dietSubType);
			}
		}

		List<ServiceMaster> serviceMasterList = serviceMasterRepository.findAll();
		if (CollectionUtils.isNotEmpty(serviceMasterList)) {
			for (ServiceMaster serviceMaster : serviceMasterList) {
				if (StringUtils.isNotEmpty(serviceMaster.getServiceItemsColumnName())) {
					serviceMasterMap.put(serviceMaster.getServiceItemsColumnName(), serviceMaster);
				}
			}
		}
	}

	public static String getMedicalComorbiditiesString(AdHocItems adHocItems) {
		List<String> list = new ArrayList<>();

		if (YesNo.YES.equals(adHocItems.getNormal())) {
			list.add(medicalComorbiditiesMap.get("normal").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getDiabeticDiet())) {
			list.add(medicalComorbiditiesMap.get("diabeticDiet").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getRenal())) {
			list.add(medicalComorbiditiesMap.get("renal").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getHypertensive())) {
			list.add(medicalComorbiditiesMap.get("hypertensive").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getSaltFree())) {
			list.add(medicalComorbiditiesMap.get("saltFree").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getFatFree())) {
			list.add(medicalComorbiditiesMap.get("fatFree").getValue());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(", ")) : "";
	}

	public static String getMedicalComorbiditiesColumnNames(AdHocItems adHocItems) {
		List<String> list = new ArrayList<>();

		if (YesNo.YES.equals(adHocItems.getNormal())) {
			list.add(medicalComorbiditiesMap.get("normal").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getDiabeticDiet())) {
			list.add(medicalComorbiditiesMap.get("diabeticDiet").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getRenal())) {
			list.add(medicalComorbiditiesMap.get("renal").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getHypertensive())) {
			list.add(medicalComorbiditiesMap.get("hypertensive").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getSaltFree())) {
			list.add(medicalComorbiditiesMap.get("saltFree").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getFatFree())) {
			list.add(medicalComorbiditiesMap.get("fatFree").getColumnName());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(",")) : "";
	}

	public static void setMedicalComorbiditiesColumnValue(AdHocItems adHocItems, String medicalComorbiditiesColumnNames) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(medicalComorbiditiesColumnNames)) {
			list = List.of(medicalComorbiditiesColumnNames.split(","));
		}
		adHocItems.setNormal(list.contains("normal") ? YesNo.YES : YesNo.NO);
		adHocItems.setDiabeticDiet(list.contains("diabeticDiet") ? YesNo.YES : YesNo.NO);
		adHocItems.setRenal(list.contains("renal") ? YesNo.YES : YesNo.NO);
		adHocItems.setHypertensive(list.contains("hypertensive") ? YesNo.YES : YesNo.NO);
		adHocItems.setSaltFree(list.contains("saltFree") ? YesNo.YES : YesNo.NO);
		adHocItems.setFatFree(list.contains("fatFree") ? YesNo.YES : YesNo.NO);
	}

	public static String getDietTypeString(AdHocItems adHocItems) {
		List<String> list = new ArrayList<>();

		if (YesNo.YES.equals(adHocItems.getFullDiet())) {
			list.add(dietTypeOralSolidMap.get("fullDiet").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getSoftDiet())) {
			list.add(dietTypeOralSolidMap.get("softDiet").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getSemiSolidDiet())) {
			list.add(dietTypeOralSolidMap.get("semiSolidDiet").getValue());
		}

		if (YesNo.YES.equals(adHocItems.getExtraLiquid())) {
			list.add("Extra Liquid");
		}

		if (YesNo.YES.equals(adHocItems.getClearLiquids())) {
			list.add(dietSubTypeMap.get("clearLiquids").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getAllLiquidsOrally())) {
			list.add(dietSubTypeMap.get("allLiquidsOrally").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getBariatrics())) {
			list.add(dietSubTypeMap.get("bariatrics").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getRtFeeding())) {
			list.add(dietSubTypeMap.get("rtFeeding").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getPegFeeding())) {
			list.add(dietSubTypeMap.get("pegFeeding").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getNgFeeding())) {
			list.add(dietSubTypeMap.get("ngFeeding").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getJjFeeding())) {
			list.add(dietSubTypeMap.get("jjFeeding").getValue());
		}
		if (YesNo.YES.equals(adHocItems.getClearLiquidsThroughTubeFeeding())) {
			list.add(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getValue());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(", ")) : "";
	}

	public static String getDietTypesColumnNames(AdHocItems adHocItems) {
		List<String> list = new ArrayList<>();

		if (YesNo.YES.equals(adHocItems.getFullDiet())) {
			list.add(dietTypeOralSolidMap.get("fullDiet").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getSoftDiet())) {
			list.add(dietTypeOralSolidMap.get("softDiet").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getSemiSolidDiet())) {
			list.add(dietTypeOralSolidMap.get("semiSolidDiet").getColumnName());
		}

		if (YesNo.YES.equals(adHocItems.getExtraLiquid())) {
			list.add("extraLiquid");
		}

		if (YesNo.YES.equals(adHocItems.getClearLiquids())) {
			list.add(dietSubTypeMap.get("clearLiquids").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getAllLiquidsOrally())) {
			list.add(dietSubTypeMap.get("allLiquidsOrally").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getBariatrics())) {
			list.add(dietSubTypeMap.get("bariatrics").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getRtFeeding())) {
			list.add(dietSubTypeMap.get("rtFeeding").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getPegFeeding())) {
			list.add(dietSubTypeMap.get("pegFeeding").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getNgFeeding())) {
			list.add(dietSubTypeMap.get("ngFeeding").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getJjFeeding())) {
			list.add(dietSubTypeMap.get("jjFeeding").getColumnName());
		}
		if (YesNo.YES.equals(adHocItems.getClearLiquidsThroughTubeFeeding())) {
			list.add(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getColumnName());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(",")) : "";
	}

	public static void setDietTypesColumnValue(AdHocItems adHocItems, String dietTypesColumnNames) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(dietTypesColumnNames)) {
			list = List.of(dietTypesColumnNames.split(","));
		}
		adHocItems.setFullDiet(list.contains("fullDiet") ? YesNo.YES : YesNo.NO);
		adHocItems.setSoftDiet(list.contains("softDiet") ? YesNo.YES : YesNo.NO);
		adHocItems.setSemiSolidDiet(list.contains("semiSolidDiet") ? YesNo.YES : YesNo.NO);
		adHocItems.setExtraLiquid(list.contains("extraLiquid") ? YesNo.YES : YesNo.NO);
		adHocItems.setClearLiquids(list.contains("clearLiquids") ? YesNo.YES : YesNo.NO);
		adHocItems.setAllLiquidsOrally(list.contains("allLiquidsOrally") ? YesNo.YES : YesNo.NO);
		adHocItems.setBariatrics(list.contains("bariatrics") ? YesNo.YES : YesNo.NO);
		adHocItems.setRtFeeding(list.contains("rtFeeding") ? YesNo.YES : YesNo.NO);
		adHocItems.setPegFeeding(list.contains("pegFeeding") ? YesNo.YES : YesNo.NO);
		adHocItems.setNgFeeding(list.contains("ngFeeding") ? YesNo.YES : YesNo.NO);
		adHocItems.setJjFeeding(list.contains("jjFeeding") ? YesNo.YES : YesNo.NO);
		adHocItems.setClearLiquidsThroughTubeFeeding(list.contains("clearLiquidsThroughTubeFeeding") ? YesNo.YES : YesNo.NO);
	}

	public static List<DietTypesColumnDto> getDietTypesColumnList(Boolean includeDietTypeOralSolid, Boolean extraLiquid) {
		List<DietTypesColumnDto> list = new ArrayList<>();
		if (includeDietTypeOralSolid) {
			if (dietTypeOralSolidMap.get("fullDiet").getIsActive()) {
				list.add(new DietTypesColumnDto(dietTypeOralSolidMap.get("fullDiet").getColumnName(), dietTypeOralSolidMap.get("fullDiet").getValue()));
			}
			if (dietTypeOralSolidMap.get("softDiet").getIsActive()) {
				list.add(new DietTypesColumnDto(dietTypeOralSolidMap.get("softDiet").getColumnName(), dietTypeOralSolidMap.get("softDiet").getValue()));
			}
			if (dietTypeOralSolidMap.get("semiSolidDiet").getIsActive()) {
				list.add(new DietTypesColumnDto(dietTypeOralSolidMap.get("semiSolidDiet").getColumnName(), dietTypeOralSolidMap.get("semiSolidDiet").getValue()));
			}
		}

		if (extraLiquid) {
			list.add(new DietTypesColumnDto("extraLiquid", "Extra Liquid"));
		}

		if (dietSubTypeMap.get("clearLiquids").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("clearLiquids").getColumnName(), dietSubTypeMap.get("clearLiquids").getValue()));
		}
		if (dietSubTypeMap.get("allLiquidsOrally").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("allLiquidsOrally").getColumnName(), dietSubTypeMap.get("allLiquidsOrally").getValue()));
		}
		if (dietSubTypeMap.get("bariatrics").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("bariatrics").getColumnName(), dietSubTypeMap.get("bariatrics").getValue()));
		}
		if (dietSubTypeMap.get("rtFeeding").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("rtFeeding").getColumnName(), dietSubTypeMap.get("rtFeeding").getValue()));
		}
		if (dietSubTypeMap.get("pegFeeding").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("pegFeeding").getColumnName(), dietSubTypeMap.get("pegFeeding").getValue()));
		}
		if (dietSubTypeMap.get("ngFeeding").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("ngFeeding").getColumnName(), dietSubTypeMap.get("ngFeeding").getValue()));
		}
		if (dietSubTypeMap.get("jjFeeding").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("jjFeeding").getColumnName(), dietSubTypeMap.get("jjFeeding").getValue()));
		}
		if (dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getIsActive()) {
			list.add(new DietTypesColumnDto(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getColumnName(), dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getValue()));
		}
		return list;
	}

	public static String getDietTypeString(ServiceItems serviceItems) {
		List<String> list = new ArrayList<>();
		if (YesNo.YES.equals(serviceItems.getExtraLiquid())) {
			list.add("Extra Liquid");
		}
		if (YesNo.YES.equals(serviceItems.getClearLiquids())) {
			list.add(dietSubTypeMap.get("clearLiquids").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getAllLiquidsOrally())) {
			list.add(dietSubTypeMap.get("allLiquidsOrally").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getBariatrics())) {
			list.add(dietSubTypeMap.get("bariatrics").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getRtFeeding())) {
			list.add(dietSubTypeMap.get("rtFeeding").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getPegFeeding())) {
			list.add(dietSubTypeMap.get("pegFeeding").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getNgFeeding())) {
			list.add(dietSubTypeMap.get("ngFeeding").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getJjFeeding())) {
			list.add(dietSubTypeMap.get("jjFeeding").getValue());
		}
		if (YesNo.YES.equals(serviceItems.getClearLiquidsThroughTubeFeeding())) {
			list.add(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getValue());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(", ")) : "";
	}

	public static String getDietTypesColumnNames(ServiceItems serviceItems) {
		List<String> list = new ArrayList<>();

		if (YesNo.YES.equals(serviceItems.getExtraLiquid())) {
			list.add("extraLiquid");
		}
		if (YesNo.YES.equals(serviceItems.getClearLiquids())) {
			list.add(dietSubTypeMap.get("clearLiquids").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getAllLiquidsOrally())) {
			list.add(dietSubTypeMap.get("allLiquidsOrally").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getBariatrics())) {
			list.add(dietSubTypeMap.get("bariatrics").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getRtFeeding())) {
			list.add(dietSubTypeMap.get("rtFeeding").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getPegFeeding())) {
			list.add(dietSubTypeMap.get("pegFeeding").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getNgFeeding())) {
			list.add(dietSubTypeMap.get("ngFeeding").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getJjFeeding())) {
			list.add(dietSubTypeMap.get("jjFeeding").getColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getClearLiquidsThroughTubeFeeding())) {
			list.add(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getColumnName());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(",")) : "";
	}

	public static void setDietTypesColumnValue(ServiceItems serviceItems, String dietTypesColumnNames) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(dietTypesColumnNames)) {
			list = List.of(dietTypesColumnNames.split(","));
		}
		serviceItems.setExtraLiquid(list.contains("extraLiquid") ? YesNo.YES : YesNo.NO);
		serviceItems.setClearLiquids(list.contains("clearLiquids") ? YesNo.YES : YesNo.NO);
		serviceItems.setAllLiquidsOrally(list.contains("allLiquidsOrally") ? YesNo.YES : YesNo.NO);
		serviceItems.setBariatrics(list.contains("bariatrics") ? YesNo.YES : YesNo.NO);
		serviceItems.setRtFeeding(list.contains("rtFeeding") ? YesNo.YES : YesNo.NO);
		serviceItems.setPegFeeding(list.contains("pegFeeding") ? YesNo.YES : YesNo.NO);
		serviceItems.setNgFeeding(list.contains("ngFeeding") ? YesNo.YES : YesNo.NO);
		serviceItems.setJjFeeding(list.contains("jjFeeding") ? YesNo.YES : YesNo.NO);
		serviceItems.setClearLiquidsThroughTubeFeeding(list.contains("clearLiquidsThroughTubeFeeding") ? YesNo.YES : YesNo.NO);
	}

	public static List<Long> getDietTypesIdList(ServiceItems serviceItems) {
		List<Long> list = new ArrayList<>();
		if (YesNo.YES.equals(serviceItems.getClearLiquids())) {
			list.add(dietSubTypeMap.get("clearLiquids").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getAllLiquidsOrally())) {
			list.add(dietSubTypeMap.get("allLiquidsOrally").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getBariatrics())) {
			list.add(dietSubTypeMap.get("bariatrics").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getRtFeeding())) {
			list.add(dietSubTypeMap.get("rtFeeding").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getPegFeeding())) {
			list.add(dietSubTypeMap.get("pegFeeding").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getNgFeeding())) {
			list.add(dietSubTypeMap.get("ngFeeding").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getJjFeeding())) {
			list.add(dietSubTypeMap.get("jjFeeding").getDietSubTypeId());
		}
		if (YesNo.YES.equals(serviceItems.getClearLiquidsThroughTubeFeeding())) {
			list.add(dietSubTypeMap.get("clearLiquidsThroughTubeFeeding").getDietSubTypeId());
		}
		return list;
	}

	public static String getServiceMasterString(ServiceItems serviceItems) {
		List<String> list = new ArrayList<>();
		if (YesNo.YES.equals(serviceItems.getS_10amExtraLiq())) {
			list.add(serviceMasterMap.get("s_10amExtraLiq").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_2pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_2pmExtraLiq").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_6pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_6pmExtraLiq").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_10pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_10pmExtraLiq").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_6am())) {
			list.add(serviceMasterMap.get("s_6am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_7am())) {
			list.add(serviceMasterMap.get("s_7am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_8am())) {
			list.add(serviceMasterMap.get("s_8am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_9am())) {
			list.add(serviceMasterMap.get("s_9am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_10am())) {
			list.add(serviceMasterMap.get("s_10am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_11am())) {
			list.add(serviceMasterMap.get("s_11am").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_12pm())) {
			list.add(serviceMasterMap.get("s_12pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_1pm())) {
			list.add(serviceMasterMap.get("s_1pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_2pm())) {
			list.add(serviceMasterMap.get("s_2pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_3pm())) {
			list.add(serviceMasterMap.get("s_3pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_4pm())) {
			list.add(serviceMasterMap.get("s_4pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_5pm())) {
			list.add(serviceMasterMap.get("s_5pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_6pm())) {
			list.add(serviceMasterMap.get("s_6pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_7pm())) {
			list.add(serviceMasterMap.get("s_7pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_8pm())) {
			list.add(serviceMasterMap.get("s_8pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_9pm())) {
			list.add(serviceMasterMap.get("s_9pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_10pm())) {
			list.add(serviceMasterMap.get("s_10pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_11pm())) {
			list.add(serviceMasterMap.get("s_11pm").getService());
		}
		if (YesNo.YES.equals(serviceItems.getS_12am())) {
			list.add(serviceMasterMap.get("s_12am").getService());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(", ")) : "";
	}

	public static String getServiceMasterColumnNames(ServiceItems serviceItems) {
		List<String> list = new ArrayList<>();
		if (YesNo.YES.equals(serviceItems.getS_10amExtraLiq())) {
			list.add(serviceMasterMap.get("s_10amExtraLiq").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_2pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_2pmExtraLiq").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_6pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_6pmExtraLiq").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_10pmExtraLiq())) {
			list.add(serviceMasterMap.get("s_10pmExtraLiq").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_6am())) {
			list.add(serviceMasterMap.get("s_6am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_7am())) {
			list.add(serviceMasterMap.get("s_7am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_8am())) {
			list.add(serviceMasterMap.get("s_8am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_9am())) {
			list.add(serviceMasterMap.get("s_9am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_10am())) {
			list.add(serviceMasterMap.get("s_10am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_11am())) {
			list.add(serviceMasterMap.get("s_11am").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_12pm())) {
			list.add(serviceMasterMap.get("s_12pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_1pm())) {
			list.add(serviceMasterMap.get("s_1pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_2pm())) {
			list.add(serviceMasterMap.get("s_2pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_3pm())) {
			list.add(serviceMasterMap.get("s_3pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_4pm())) {
			list.add(serviceMasterMap.get("s_4pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_5pm())) {
			list.add(serviceMasterMap.get("s_5pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_6pm())) {
			list.add(serviceMasterMap.get("s_6pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_7pm())) {
			list.add(serviceMasterMap.get("s_7pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_8pm())) {
			list.add(serviceMasterMap.get("s_8pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_9pm())) {
			list.add(serviceMasterMap.get("s_9pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_10pm())) {
			list.add(serviceMasterMap.get("s_10pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_11pm())) {
			list.add(serviceMasterMap.get("s_11pm").getServiceItemsColumnName());
		}
		if (YesNo.YES.equals(serviceItems.getS_12am())) {
			list.add(serviceMasterMap.get("s_12am").getServiceItemsColumnName());
		}
		return CollectionUtils.isNotEmpty(list) ? list.stream().collect(Collectors.joining(",")) : "";
	}

	public static void setServiceMasterColumnValue(ServiceItems serviceItems, String serviceMasterColumnNames) {
		List<String> list = new ArrayList<>();
		if (StringUtils.isNotEmpty(serviceMasterColumnNames)) {
			list = List.of(serviceMasterColumnNames.split(","));
		}
		serviceItems.setS_10amExtraLiq(list.contains("s_10amExtraLiq") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_2pmExtraLiq(list.contains("s_2pmExtraLiq") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_6pmExtraLiq(list.contains("s_6pmExtraLiq") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_10pmExtraLiq(list.contains("s_10pmExtraLiq") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_6am(list.contains("s_6am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_7am(list.contains("s_7am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_8am(list.contains("s_8am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_9am(list.contains("s_9am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_10am(list.contains("s_10am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_11am(list.contains("s_11am") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_12pm(list.contains("s_12pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_1pm(list.contains("s_1pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_2pm(list.contains("s_2pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_3pm(list.contains("s_3pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_4pm(list.contains("s_4pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_5pm(list.contains("s_5pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_6pm(list.contains("s_6pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_7pm(list.contains("s_7pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_8pm(list.contains("s_8pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_9pm(list.contains("s_9pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_10pm(list.contains("s_10pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_11pm(list.contains("s_11pm") ? YesNo.YES : YesNo.NO);
		serviceItems.setS_12am(list.contains("s_12am") ? YesNo.YES : YesNo.NO);
	}

	public static Specification<ServiceItems> getServiceItemsSpecificationForValidation(ServiceItems serviceItems) {
		Specification<ServiceItems> specification = (root, query, criteriaBuilder) -> {
			List<Predicate> finalPredicates = new ArrayList<>();
			List<Predicate> predicateDietTypeList = new ArrayList<>();
			List<Predicate> predicateServiceMaster = new ArrayList<>();

			// finalPredicates
			finalPredicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
			if (ObjectUtils.isNotEmpty(serviceItems.getServiceItemsId()) && serviceItems.getServiceItemsId() > 0) {
				finalPredicates.add(criteriaBuilder.notEqual(root.get("serviceItemsId"), serviceItems.getServiceItemsId()));
			}

			// predicateDietTypeList
			if (serviceItems.getExtraLiquid().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("extraLiquid"), YesNo.YES));
			}
			if (serviceItems.getClearLiquids().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("clearLiquids"), YesNo.YES));
			}
			if (serviceItems.getAllLiquidsOrally().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("allLiquidsOrally"), YesNo.YES));
			}
			if (serviceItems.getBariatrics().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("bariatrics"), YesNo.YES));
			}
			if (serviceItems.getRtFeeding().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("rtFeeding"), YesNo.YES));
			}
			if (serviceItems.getPegFeeding().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("pegFeeding"), YesNo.YES));
			}
			if (serviceItems.getNgFeeding().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("ngFeeding"), YesNo.YES));
			}
			if (serviceItems.getJjFeeding().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("jjFeeding"), YesNo.YES));
			}
			if (serviceItems.getClearLiquidsThroughTubeFeeding().equals(YesNo.YES)) {
				predicateDietTypeList.add(criteriaBuilder.equal(root.get("clearLiquidsThroughTubeFeeding"), YesNo.YES));
			}
			// predicateServiceMaster
			if (serviceItems.getS_10amExtraLiq().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_10amExtraLiq"), YesNo.YES));
			}
			if (serviceItems.getS_2pmExtraLiq().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_2pmExtraLiq"), YesNo.YES));
			}
			if (serviceItems.getS_6pmExtraLiq().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_6pmExtraLiq"), YesNo.YES));
			}
			if (serviceItems.getS_10pmExtraLiq().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_10pmExtraLiq"), YesNo.YES));
			}
			if (serviceItems.getS_6am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_6am"), YesNo.YES));
			}
			if (serviceItems.getS_7am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_7am"), YesNo.YES));
			}
			if (serviceItems.getS_8am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_8am"), YesNo.YES));
			}
			if (serviceItems.getS_9am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_9am"), YesNo.YES));
			}
			if (serviceItems.getS_10am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_10am"), YesNo.YES));
			}
			if (serviceItems.getS_11am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_11am"), YesNo.YES));
			}
			if (serviceItems.getS_12pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_12pm"), YesNo.YES));
			}
			if (serviceItems.getS_1pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_1pm"), YesNo.YES));
			}
			if (serviceItems.getS_2pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_2pm"), YesNo.YES));
			}
			if (serviceItems.getS_3pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_3pm"), YesNo.YES));
			}
			if (serviceItems.getS_4pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_4pm"), YesNo.YES));
			}
			if (serviceItems.getS_5pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_5pm"), YesNo.YES));
			}
			if (serviceItems.getS_6pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_6pm"), YesNo.YES));
			}
			if (serviceItems.getS_7pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_7pm"), YesNo.YES));
			}
			if (serviceItems.getS_8pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_8pm"), YesNo.YES));
			}
			if (serviceItems.getS_9pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_9pm"), YesNo.YES));
			}
			if (serviceItems.getS_10pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_10pm"), YesNo.YES));
			}
			if (serviceItems.getS_11pm().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_11pm"), YesNo.YES));
			}
			if (serviceItems.getS_12am().equals(YesNo.YES)) {
				predicateServiceMaster.add(criteriaBuilder.equal(root.get("s_12am"), YesNo.YES));
			}
			return criteriaBuilder.and(criteriaBuilder.and(finalPredicates.toArray(new Predicate[0])),
					criteriaBuilder.or(predicateDietTypeList.toArray(new Predicate[0])),
					criteriaBuilder.or(predicateServiceMaster.toArray(new Predicate[0])));
		};
		return specification;
	}

}
