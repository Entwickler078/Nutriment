package com.HospitalManagementSystem.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Data
public class ServiceItemsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long serviceItemsId;

	private String itemName;

	private YesNo s_10amExtraLiq;
	private YesNo s_2pmExtraLiq;
	private YesNo s_6pmExtraLiq;
	private YesNo s_10pmExtraLiq;

	private YesNo s_6am;
	private YesNo s_7am;
	private YesNo s_8am;
	private YesNo s_9am;
	private YesNo s_10am;
	private YesNo s_11am;
	private YesNo s_12pm;
	private YesNo s_1pm;
	private YesNo s_2pm;
	private YesNo s_3pm;
	private YesNo s_4pm;
	private YesNo s_5pm;
	private YesNo s_6pm;
	private YesNo s_7pm;
	private YesNo s_8pm;
	private YesNo s_9pm;
	private YesNo s_10pm;
	private YesNo s_11pm;
	private YesNo s_12am;

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

	private BigDecimal calories = new BigDecimal("0.00");
	private BigDecimal protein = new BigDecimal("0.00");

	private String dietTypeString;
	private String dietTypesColumnNames;
	private String serviceMasterString;
	private String serviceMasterColumnNames;

}
