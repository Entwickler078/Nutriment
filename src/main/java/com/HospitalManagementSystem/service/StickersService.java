package com.HospitalManagementSystem.service;

import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

public interface StickersService {

	String stickers(Model model, Long patientId);

	ResponseEntity<Resource> generateStickers(String dateSelection, Long serviceMasterId, Long patientId);

	ResponseEntity<Resource> generateAdhocOrderStickers(Long adHocOrderId);

	boolean isDietTypeLiquidOralTF(Long serviceMasterId);

}
