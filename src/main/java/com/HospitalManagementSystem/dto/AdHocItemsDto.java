package com.HospitalManagementSystem.dto;

import java.math.BigDecimal;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Data
public class AdHocItemsDto {

	private Long adHocItemsId;

	private String itemName;
	private BigDecimal rate = new BigDecimal("0.00");

	private YesNo fullDiet;
	private YesNo softDiet;
	private YesNo semiSolidDiet;

	private YesNo normal;
	private YesNo diabeticDiet;
	private YesNo renal;
	private YesNo hypertensive;
	private YesNo saltFree;
	private YesNo fatFree;

	private YesNo extraLiquid;
	private YesNo clearLiquids;
	private YesNo allLiquidsOrally;
	private YesNo bariatrics;
	private YesNo rtFeeding;
	private YesNo pegFeeding;
	private YesNo ngFeeding;
	private YesNo jjFeeding;
	private YesNo clearLiquidsThroughTubeFeeding;

	private Boolean isActive = Boolean.FALSE;

	private String medicalComorbiditiesColumnNames;
	private String dietTypesColumnNames;

}
