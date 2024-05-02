package com.HospitalManagementSystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocItemSearchDto;
import com.HospitalManagementSystem.dto.AdHocItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.AdHocItemsDto;
import com.HospitalManagementSystem.dto.ServiceItemSearchDto;
import com.HospitalManagementSystem.dto.ServiceItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.ServiceItemsDto;
import com.HospitalManagementSystem.service.MasterDataService;

@Controller
@RequestMapping("/master-data")
public class MasterDataController {

	@Autowired
	private MasterDataService masterDataService;

	@GetMapping("/adhoc-item-listing")
	public String adHocItemListing(Model model) {
		return masterDataService.adHocItemListing(model);
	}

	@PostMapping("/adhoc-item-listing-data")
	@ResponseBody
	public AdHocItemsDataTablesOutputDto adHocItemListingData(@RequestBody AdHocItemSearchDto adHocItemSearchDto) {
		return masterDataService.adHocItemListingData(adHocItemSearchDto);
	}

	@GetMapping("/adhoc-item-details")
	public String adHocItemDetails(@RequestParam(name = "adHocItemsId", required = false) Long adHocItemsId, Model model) {
		return masterDataService.getAdHocItemDetails(adHocItemsId, model);
	}

	@PostMapping("/adhoc-item-details")
	public String saveAdHocItemDetails(RedirectAttributes redir, @ModelAttribute("adHocItemsDto") AdHocItemsDto adHocItemsDto) {
		return masterDataService.saveAdHocItemDetails(redir, adHocItemsDto);
	}

	@DeleteMapping("/delete-adhoc-item")
	@ResponseBody
	public ResponseEntity<String> deleteAdHocItem(@RequestParam("adHocItemsId") Long adHocItemsId) {
		return masterDataService.deleteAdHocItem(adHocItemsId);
	}

	@GetMapping("/service-item-listing")
	public String serviceItemListing(Model model) {
		return masterDataService.serviceItemListing(model);
	}

	@PostMapping("/service-item-listing-data")
	@ResponseBody
	public ServiceItemsDataTablesOutputDto serviceItemListingData(@RequestBody ServiceItemSearchDto serviceItemSearchDto) {
		return masterDataService.serviceItemListingData(serviceItemSearchDto);
	}

	@GetMapping("/service-item-details")
	public String serviceItemDetails(@RequestParam(name = "serviceItemsId", required = false) Long serviceItemsId, Model model) {
		return masterDataService.getServiceItemDetails(serviceItemsId, model);
	}

	@PostMapping("/service-item-details")
	public String saveServiceItemDetails(RedirectAttributes redir, @ModelAttribute("serviceItemsDto") ServiceItemsDto serviceItemsDto) {
		return masterDataService.saveServiceItemDetails(redir, serviceItemsDto);
	}

	@DeleteMapping("/delete-service-item")
	@ResponseBody
	public ResponseEntity<String> deleteServiceItem(@RequestParam("serviceItemsId") Long serviceItemsId) {
		return masterDataService.deleteServiceItem(serviceItemsId);
	}

}
