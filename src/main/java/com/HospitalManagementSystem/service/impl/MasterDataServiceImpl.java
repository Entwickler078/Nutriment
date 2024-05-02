package com.HospitalManagementSystem.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.Search;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.HospitalManagementSystem.dto.AdHocItemSearchDto;
import com.HospitalManagementSystem.dto.AdHocItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.AdHocItemsDto;
import com.HospitalManagementSystem.dto.ServiceItemSearchDto;
import com.HospitalManagementSystem.dto.ServiceItemsDataTablesOutputDto;
import com.HospitalManagementSystem.dto.ServiceItemsDto;
import com.HospitalManagementSystem.entity.User;
import com.HospitalManagementSystem.entity.history.AdHocItemsHistory;
import com.HospitalManagementSystem.entity.history.ServiceItemsHistory;
import com.HospitalManagementSystem.entity.master.AdHocItems;
import com.HospitalManagementSystem.entity.master.ServiceItems;
import com.HospitalManagementSystem.repository.AdHocItemsDataTablesRepository;
import com.HospitalManagementSystem.repository.AdHocItemsHistoryRepository;
import com.HospitalManagementSystem.repository.AdHocItemsRepository;
import com.HospitalManagementSystem.repository.MedicalComorbiditiesRepository;
import com.HospitalManagementSystem.repository.ServiceItemsDataTablesRepository;
import com.HospitalManagementSystem.repository.ServiceItemsHistoryRepository;
import com.HospitalManagementSystem.repository.ServiceItemsRepository;
import com.HospitalManagementSystem.repository.ServiceMasterRepository;
import com.HospitalManagementSystem.service.DietPlanService;
import com.HospitalManagementSystem.service.MasterDataService;
import com.HospitalManagementSystem.utility.CommonUtility;
import com.HospitalManagementSystem.utility.MasterDataUtility;

@Service
@SuppressWarnings("unchecked")
public class MasterDataServiceImpl implements MasterDataService {

	@PersistenceContext
	EntityManager em;

	@Autowired
	private AdHocItemsDataTablesRepository adHocItemsDataTablesRepository;
	@Autowired
	private AdHocItemsHistoryRepository adHocItemsHistoryRepository;
	@Autowired
	private AdHocItemsRepository adHocItemsRepository;
	@Autowired
	private MedicalComorbiditiesRepository medicalComorbiditiesRepository;
	@Autowired
	private ServiceItemsDataTablesRepository serviceItemsDataTablesRepository;
	@Autowired
	private ServiceItemsRepository serviceItemsRepository;
	@Autowired
	private ServiceItemsHistoryRepository serviceItemsHistoryRepository;
	@Autowired
	private ServiceMasterRepository serviceMasterRepository;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private CommonUtility commonUtility;

	@Autowired
	private DietPlanService dietPlanService;

	@Override
	public String adHocItemListing(Model model) {
		return "masterData/AdHocItemListing";
	}

	@Override
	public AdHocItemsDataTablesOutputDto adHocItemListingData(AdHocItemSearchDto adHocItemSearchDto) {
		DataTablesInput input = adHocItemSearchDto;
		input.setSearch(new Search(adHocItemSearchDto.getSearchText(), false));

		Specification<AdHocItems> additionalSpecification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		AdHocItemsDataTablesOutputDto adHocItemsDataTablesOutputDto = new AdHocItemsDataTablesOutputDto();
		adHocItemsDataTablesOutputDto.setData(adHocItemsDataTablesRepository.findAll(input, additionalSpecification));
		adHocItemsDataTablesOutputDto.setCount(adHocItemsDataTablesRepository.countByIsActive(Boolean.TRUE));
		return adHocItemsDataTablesOutputDto;
	}

	@Override
	public String getAdHocItemDetails(Long adHocItemsId, Model model) {
		if (ObjectUtils.isNotEmpty(adHocItemsId)) {
			AdHocItems adHocItems = adHocItemsRepository.findById(adHocItemsId).get();
			AdHocItemsDto adHocItemsDto = modelMapper.map(adHocItems, AdHocItemsDto.class);
			model.addAttribute("adHocItemsDto", adHocItemsDto);
		} else {
			model.addAttribute("adHocItemsDto", new AdHocItemsDto());
		}
		model.addAttribute("medicalComorbiditiesList", medicalComorbiditiesRepository.findAllByIsActive(Boolean.TRUE));
		model.addAttribute("dietTypesColumnList", MasterDataUtility.getDietTypesColumnList(Boolean.TRUE, Boolean.FALSE));
		return "masterData/AdHocItem";
	}

	@Override
	public String saveAdHocItemDetails(RedirectAttributes redir, AdHocItemsDto adHocItemsDto) {
		boolean isValid = true;
		Optional<AdHocItems> adHocItems = Optional.empty();
		if (ObjectUtils.isNotEmpty(adHocItemsDto.getAdHocItemsId()) && adHocItemsDto.getAdHocItemsId() > 0) {
			adHocItems = adHocItemsRepository.findById(adHocItemsDto.getAdHocItemsId());
		}
		if (adHocItems.isPresent() && !adHocItems.get().getIsActive()) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "AdHoc Item not found");
		}
		if (!isValid) {
			return "redirect:/master-data/adhoc-item-listing";
		}

		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		AdHocItems saveAdHocItems = modelMapper.map(adHocItemsDto, AdHocItems.class);
		saveAdHocItems.setIsActive(Boolean.TRUE);
		if (adHocItems.isPresent()) {
			AdHocItems adHocItemsEntity = adHocItems.get();
			saveAdHocItems.setItemName(adHocItemsEntity.getItemName());

			saveAdHocItems.setCreatedOn(adHocItemsEntity.getCreatedOn());
			saveAdHocItems.setCreatedBy(adHocItemsEntity.getCreatedBy());
			saveAdHocItems.setModifiedOn(now);
			saveAdHocItems.setModifiedBy(currentUser.getUserId());
			saveAdHocItems.setCreatedUserHistoryId(adHocItemsEntity.getCreatedUserHistoryId());
			saveAdHocItems.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		} else {
			saveAdHocItems.setCreatedOn(now);
			saveAdHocItems.setCreatedBy(currentUser.getUserId());
			saveAdHocItems.setModifiedOn(now);
			saveAdHocItems.setModifiedBy(currentUser.getUserId());
			saveAdHocItems.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveAdHocItems.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
		}
		MasterDataUtility.setMedicalComorbiditiesColumnValue(saveAdHocItems, adHocItemsDto.getMedicalComorbiditiesColumnNames());
		MasterDataUtility.setDietTypesColumnValue(saveAdHocItems, adHocItemsDto.getDietTypesColumnNames());
		saveAdHocItems = saveAdHocItems(saveAdHocItems);
		return "redirect:/master-data/adhoc-item-listing";
	}

	public AdHocItems saveAdHocItems(AdHocItems adHocItems) {
		adHocItems = adHocItemsRepository.save(adHocItems);
		AdHocItemsHistory adHocItemsHistory = modelMapper.map(adHocItems, AdHocItemsHistory.class);
		adHocItemsHistory.setHistoryCreatedOn(adHocItems.getModifiedOn());
		adHocItemsHistory.setHistoryCreatedBy(adHocItems.getModifiedBy());
		adHocItemsHistory = adHocItemsHistoryRepository.save(adHocItemsHistory);
		return adHocItems;
	}

	@Override
	public ResponseEntity<String> deleteAdHocItem(Long adHocItemsId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<AdHocItems> adHocItems = adHocItemsRepository.findById(adHocItemsId);
		if (adHocItems.isPresent() && !adHocItems.get().getIsActive()) {
			return ResponseEntity.ok().body("AdHoc Item not found");
		}
		if (adHocItems.isPresent()) {
			AdHocItems saveAdHocItems = adHocItems.get();
			saveAdHocItems.setIsActive(Boolean.FALSE);
			saveAdHocItems.setModifiedOn(now);
			saveAdHocItems.setModifiedBy(currentUser.getUserId());
			saveAdHocItems.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveAdHocItems(saveAdHocItems);
			return ResponseEntity.ok().body("AdHoc Item has been deleted");
		}
		return ResponseEntity.badRequest().body("AdHoc Item not found");
	}

	@Override
	public String serviceItemListing(Model model) {
		return "masterData/ServiceItemListing";
	}

	@Override
	public ServiceItemsDataTablesOutputDto serviceItemListingData(ServiceItemSearchDto serviceItemSearchDto) {
		DataTablesInput input = serviceItemSearchDto;
		input.setSearch(new Search(serviceItemSearchDto.getSearchText(), false));

		Specification<ServiceItems> additionalSpecification = (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			predicates.add(criteriaBuilder.equal(root.get("isActive"), Boolean.TRUE));
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
		ServiceItemsDataTablesOutputDto serviceItemsDataTablesOutputDto = new ServiceItemsDataTablesOutputDto();
		serviceItemsDataTablesOutputDto.setData(serviceItemsDataTablesRepository.findAll(input, additionalSpecification));
		serviceItemsDataTablesOutputDto.setCount(serviceItemsDataTablesRepository.countByIsActive(Boolean.TRUE));
		return serviceItemsDataTablesOutputDto;
	}

	@Override
	public String getServiceItemDetails(Long serviceItemsId, Model model) {
		if (ObjectUtils.isNotEmpty(serviceItemsId)) {
			ServiceItems serviceItems = serviceItemsRepository.findById(serviceItemsId).get();
			ServiceItemsDto serviceItemsDto = modelMapper.map(serviceItems, ServiceItemsDto.class);
			model.addAttribute("serviceItemsDto", serviceItemsDto);
		} else {
			if (!model.containsAttribute("serviceItemsDto")) {
				model.addAttribute("serviceItemsDto", new ServiceItemsDto());
			}
		}
		model.addAttribute("dietTypesColumnList", MasterDataUtility.getDietTypesColumnList(Boolean.FALSE, Boolean.TRUE));
		model.addAttribute("serviceMasterList", serviceMasterRepository.findAllByIsActiveAndServiceItemsColumnNameIsNotNull(Boolean.TRUE));
		return "masterData/ServiceItem";
	}

	@Override
	@Transactional
	public String saveServiceItemDetails(RedirectAttributes redir, ServiceItemsDto serviceItemsDto) {
		boolean isValid = true;
		Optional<ServiceItems> serviceItems = Optional.empty();
		if (ObjectUtils.isNotEmpty(serviceItemsDto.getServiceItemsId()) && serviceItemsDto.getServiceItemsId() > 0) {
			serviceItems = serviceItemsRepository.findById(serviceItemsDto.getServiceItemsId());
		}
		if (serviceItems.isPresent() && !serviceItems.get().getIsActive()) {
			isValid = false;
			redir.addFlashAttribute("errorMsg", "Service Item not found");
		}
		if (!isValid) {
			return "redirect:/master-data/service-item-listing";
		}

		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		ServiceItems saveServiceItems = modelMapper.map(serviceItemsDto, ServiceItems.class);
		saveServiceItems.setIsActive(Boolean.TRUE);
		MasterDataUtility.setDietTypesColumnValue(saveServiceItems, serviceItemsDto.getDietTypesColumnNames());
		MasterDataUtility.setServiceMasterColumnValue(saveServiceItems, serviceItemsDto.getServiceMasterColumnNames());
		if (isValid) {
			List<ServiceItems> serviceItemsList = serviceItemsRepository.findAll(MasterDataUtility.getServiceItemsSpecificationForValidation(saveServiceItems));
			if (CollectionUtils.isNotEmpty(serviceItemsList)) {
				isValid = false;
				redir.addFlashAttribute("serviceItemsDto", serviceItemsDto);
				redir.addFlashAttribute("errorMsg", "Item is already added for the selected diet type and service");
				return "redirect:/master-data/service-item-details" + (ObjectUtils.isNotEmpty(serviceItemsDto.getServiceItemsId()) && serviceItemsDto.getServiceItemsId() > 0 ? "?serviceItemsId=" + serviceItemsDto.getServiceItemsId() : "");
			}
		}
		if (serviceItems.isPresent()) {
			ServiceItems serviceItemsEntity = serviceItems.get();
			serviceItemsEntity.setCalories(saveServiceItems.getCalories());
			serviceItemsEntity.setProtein(saveServiceItems.getProtein());

			serviceItemsEntity.setModifiedOn(now);
			serviceItemsEntity.setModifiedBy(currentUser.getUserId());
			serviceItemsEntity.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveServiceItems = saveServiceItems(serviceItemsEntity);
		} else {
			saveServiceItems.setCreatedOn(now);
			saveServiceItems.setCreatedBy(currentUser.getUserId());
			saveServiceItems.setModifiedOn(now);
			saveServiceItems.setModifiedBy(currentUser.getUserId());
			saveServiceItems.setCreatedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveServiceItems.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());

			saveServiceItems = saveServiceItems(saveServiceItems);
			dietPlanService.addDietPlanForServiceItem(saveServiceItems, now, currentUser);
		}
		return "redirect:/master-data/service-item-listing";
	}

	public ServiceItems saveServiceItems(ServiceItems serviceItems) {
		serviceItems = serviceItemsRepository.save(serviceItems);
		ServiceItemsHistory serviceItemHistory = modelMapper.map(serviceItems, ServiceItemsHistory.class);
		serviceItemHistory.setHistoryCreatedOn(serviceItems.getModifiedOn());
		serviceItemHistory.setHistoryCreatedBy(serviceItems.getModifiedBy());
		serviceItemHistory = serviceItemsHistoryRepository.save(serviceItemHistory);
		return serviceItems;
	}

	@Override
	public ResponseEntity<String> deleteServiceItem(Long serviceItemsId) {
		LocalDateTime now = LocalDateTime.now();
		User currentUser = commonUtility.getCurrentUser();
		Optional<ServiceItems> serviceItems = serviceItemsRepository.findById(serviceItemsId);
		if (serviceItems.isPresent() && !serviceItems.get().getIsActive()) {
			return ResponseEntity.ok().body("Service Item not found");
		}
		if (serviceItems.isPresent()) {
			ServiceItems saveServiceItems = serviceItems.get();
			saveServiceItems.setIsActive(Boolean.FALSE);

			saveServiceItems.setModifiedOn(now);
			saveServiceItems.setModifiedBy(currentUser.getUserId());
			saveServiceItems.setModifiedUserHistoryId(currentUser.getCurrenUserHistoryId());
			saveServiceItems(saveServiceItems);

			dietPlanService.deleteDietPlanForServiceItem(saveServiceItems, now);
			return ResponseEntity.ok().body("Service Item has been deleted");
		}
		return ResponseEntity.badRequest().body("Service Item not found");
	}

}
