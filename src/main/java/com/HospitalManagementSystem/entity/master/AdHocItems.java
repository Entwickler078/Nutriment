package com.HospitalManagementSystem.entity.master;

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
import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;

import com.HospitalManagementSystem.enums.YesNo;
import com.HospitalManagementSystem.utility.MasterDataUtility;

import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "adhoc_items")
@Data
public class AdHocItems implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long adHocItemsId;

	private String itemName;

	@Column(precision = 13, scale = 2)
	private BigDecimal rate;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo fullDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo softDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo semiSolidDiet;

	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo normal;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo diabeticDiet;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo renal;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo hypertensive;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo saltFree;
	@Column(columnDefinition = "ENUM('YES', 'NO')")
	@Enumerated(EnumType.STRING)
	private YesNo fatFree;

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

	private LocalDateTime createdOn;

	private Long createdBy;

	private LocalDateTime modifiedOn;

	private Long modifiedBy;

	private Long createdUserHistoryId;

	private Long modifiedUserHistoryId;

	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String medicalComorbiditiesString;

	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String medicalComorbiditiesColumnNames;

	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String dietTypeString;

	@Getter(lombok.AccessLevel.NONE)
	@Transient
	private String dietTypesColumnNames;

	public String getMedicalComorbiditiesString() {
		this.setMedicalComorbiditiesString(StringUtils.isEmpty(medicalComorbiditiesString) ? MasterDataUtility.getMedicalComorbiditiesString(this) : medicalComorbiditiesString);
		return medicalComorbiditiesString;
	}

	public String getMedicalComorbiditiesColumnNames() {
		this.setMedicalComorbiditiesColumnNames(StringUtils.isEmpty(medicalComorbiditiesColumnNames) ? MasterDataUtility.getMedicalComorbiditiesColumnNames(this) : medicalComorbiditiesColumnNames);
		return medicalComorbiditiesColumnNames;
	}

	public String getDietTypeString() {
		this.setDietTypeString(StringUtils.isEmpty(dietTypeString) ? MasterDataUtility.getDietTypeString(this) : dietTypeString);
		return dietTypeString;
	}

	public String getDietTypesColumnNames() {
		this.setDietTypesColumnNames(StringUtils.isEmpty(dietTypesColumnNames) ? MasterDataUtility.getDietTypesColumnNames(this) : dietTypesColumnNames);
		return dietTypesColumnNames;
	}

}
