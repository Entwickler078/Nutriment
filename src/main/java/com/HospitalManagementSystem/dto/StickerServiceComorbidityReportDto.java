package com.HospitalManagementSystem.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import lombok.Data;
import lombok.Getter;

@Data
public class StickerServiceComorbidityReportDto {

	private String comorbidityValue;
	
	private Integer total = 0;

	private List<String> dietInstructions = new ArrayList<>();
	
	@Getter(lombok.AccessLevel.NONE)
	private String dietInstructionsReportDataString;
	
	private List<String> nursingInstructions = new ArrayList<>();
	
	@Getter(lombok.AccessLevel.NONE)
	private String nursingInstructionsReportDataString;

	public String getDietInstructionsReportDataString() {
		this.setDietInstructionsReportDataString(
				StringUtils.isEmpty(dietInstructionsReportDataString) && CollectionUtils.isNotEmpty(dietInstructions)
						? IntStream.range(0, dietInstructions.size()).mapToObj(x -> (x + 1) + ". " + dietInstructions.get(x)).collect(Collectors.joining("\n"))
						: dietInstructionsReportDataString);
		return dietInstructionsReportDataString;
	}

	public String getNursingInstructionsReportDataString() {
		this.setNursingInstructionsReportDataString(
				StringUtils.isEmpty(nursingInstructionsReportDataString) && CollectionUtils.isNotEmpty(nursingInstructions)
						? IntStream.range(0, nursingInstructions.size()).mapToObj(x -> (x + 1) + ". " + nursingInstructions.get(x)).collect(Collectors.joining("\n"))
						: nursingInstructionsReportDataString);
		return nursingInstructionsReportDataString;
	}
	
}
