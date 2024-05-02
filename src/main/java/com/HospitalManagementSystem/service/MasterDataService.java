package com.HospitalManagementSystem.service;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocItemSearchDto;
import com.HospitalManagementSystem.dto.AdHocItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.AdHocItemsDto;
import com.HospitalManagementSystem.dto.ServiceItemSearchDto;
import com.HospitalManagementSystem.dto.ServiceItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.ServiceItemsDto;

public interface MasterDataService {

	String adHocItemListing(Model model);

	AdHocItemsDataTablesOutputDto adHocItemListingData(AdHocItemSearchDto adHocItemSearchDto);

	String getAdHocItemDetails(Long adHocItemsId, Model model);

	String saveAdHocItemDetails(RedirectAttributes redir, AdHocItemsDto adHocItemsDto);

	ResponseEntity<String> deleteAdHocItem(Long adHocItemsId);

	String serviceItemListing(Model model);

	ServiceItemsDataTablesOutputDto serviceItemListingData(ServiceItemSearchDto serviceItemSearchDto);

	String getServiceItemDetails(Long serviceItemsId, Model model);

	String saveServiceItemDetails(RedirectAttributes redir, ServiceItemsDto serviceItemsDto);

	ResponseEntity<String> deleteServiceItem(Long serviceItemsId);

}
