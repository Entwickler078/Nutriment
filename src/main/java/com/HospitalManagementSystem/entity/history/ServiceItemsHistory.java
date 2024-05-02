package com.HospitalManagementSystem.entity.history;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.HospitalManagementSystem.enums.YesNo;

import lombok.Data;

@Entity
@Table(name = "service_items_history")
@Data
public class ServiceItemsHistory implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long serviceItemsHistoryId;

	private Long serviceItemsId;

	private String itemName;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_10amExtraLiq;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_2pmExtraLiq;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_6pmExtraLiq;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_10pmExtraLiq;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_6am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_7am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_8am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_9am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_10am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_11am;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_12pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_1pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_2pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_3pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_4pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_5pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_6pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_7pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_8pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_9pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_10pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_11pm;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo s_12am;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo extraLiquid;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo clearLiquids;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo allLiquidsOrally;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo bariatrics;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo rtFeeding;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo pegFeeding;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo ngFeeding;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo jjFeeding;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo clearLiquidsThroughTubeFeeding;

	private Boolean isActive = Boolean.FALSE;

	private BigDecimal calories;

	private BigDecimal protein;

	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;

	private LocalDateTime historyCreatedOn;

	private Long historyCreatedBy;

	@Column(columnDefinition = "longtext")
	private String dietTypeString;

	@Column(columnDefinition = "longtext")
	private String dietTypesColumnNames;

	@Column(columnDefinition = "longtext")
	private String serviceMasterString;

	@Column(columnDefinition = "longtext")
	private String serviceMasterColumnNames;

}
