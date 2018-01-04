package com.anksys.bgms.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anksys.bgms.model.Bank;
import com.anksys.bgms.model.Contract;
import com.anksys.bgms.model.Contractor;
import com.anksys.bgms.service.BGMSService;

/**
 * 
 * @author hsingh36
 *
 */
@Controller
public class HomeController {

	private static final Logger logger = Logger.getLogger(HomeController.class);

	private BGMSService bgmsService;

	public HomeController() {
		logger.info("Initialization Complete..");
	}

	public BGMSService getBgmsService() {
		return bgmsService;
	}

	@Autowired
	public void setBgmsService(BGMSService bgmsService) {
		this.bgmsService = bgmsService;
	}

	public List<Contractor> getAllContractors() {
		return bgmsService.getAllContractors();
	}

	public List<Bank> getAllBanks() {
		return bgmsService.getAllBanks();
	}

	private ModelMap getCsrfToken(HttpServletRequest request, ModelMap model){
		
		CsrfToken csrfToken = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
		if(csrfToken != null){
			model.addAttribute("csrfValue", csrfToken.getToken());
		}
		return model;
	}
	
	/**
	 * Helps redirect from index.jsp to login.vm
	 * 
	 * @param response
	 * @return
	 * @throws IOException
	 * 
	 */
	@RequestMapping(value = "/login.html", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
		@RequestParam(value = "logout", required = false) String logout, HttpServletRequest request) {
		logger.info("View: LoginPage");
	  ModelMap model = new ModelMap();
	  if (error != null) {
		model.addAttribute("error", "Invalid username and password!");
	  }
 
	  if (logout != null) {
		model.addAttribute("msg", "You've been logged out successfully.");
	  }
	  model = getCsrfToken(request,model);
	  return new ModelAndView("loginPage","result",model);
 
	}
	
	/**
	 * Redirects to Executer-Registration-Page.(should only be visible to
	 * ADMIN.)
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/executerRegistratioPage.html")
	public ModelAndView showExecuterRegistrationPage(HttpServletRequest request) {
		ModelMap model = new ModelMap();
		
		return new ModelAndView("registration_executer","result",getCsrfToken(request,model));
	}

	/**
	 * Redirects to Contractor Registration page.
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/contractorRegistrationPage.html")
	public ModelAndView showContractorRegistrationPage(HttpServletRequest request) {
		ModelMap model = new ModelMap();
		return new ModelAndView("registration_contractor","result",getCsrfToken(request, model));
	}

	/**
	 * Redirects to Bank Registration page.
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/bankRegistrationPage.html")
	public ModelAndView showBankRegistrationPage(HttpServletRequest request) {
		ModelMap model = new ModelMap();
		return new ModelAndView("registration_bank","result",getCsrfToken(request, model));
	}
	
	@RequestMapping(value="user/home.html")
	public ModelAndView showHomePage(HttpServletRequest request,ModelMap model){
		
		if(model==null){
			model = new ModelMap();
		}
		return new ModelAndView("bgms_list","result", getCsrfToken(request, model));
	}

	/**
	 * Return JSON object containing all contracts list.
	 * 
	 * @return
	 */
	@RequestMapping(value = "user/contractData.json",method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public List<Contract> showContractList(HttpServletRequest request,ModelMap model) {
		if(model==null){
			model = new ModelMap();
		}
		List<Contract> contractList = bgmsService.getAllContracts();
		return contractList;
	}
	
	@RequestMapping(value="user/getContractListBasedOnSearch.json", method=RequestMethod.GET)
	@ResponseBody
	public List<String> getContractListBasedOnSearch(HttpServletRequest request){
		List<Contract> contractorList = bgmsService.getContractListBasedOnSearch(request.getParameter("contractId"));
		List<String> listOfContractId = new ArrayList<String>();
		for(Contract contract: contractorList){
			listOfContractId.add(contract.getContractId());
		}
		return listOfContractId;
	}
	
	@RequestMapping(value="user/extendValidity.do", method=RequestMethod.POST)
	public ModelAndView extendValidityDate(@RequestParam(value="contractId") String contractId,@RequestParam(value="extendedDate") String extendedDate,HttpServletRequest request){
		ModelMap model = new ModelMap();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+5.30"));
		try{
			Contract  contract = new Contract();
			contract.setContractId(contractId);
			contract.setExtendedUpto(sdf.parse(extendedDate));
			if(!bgmsService.extendValidityDate(contract)){
				model.addAttribute("message","Please enter correct ContractId or Extention date!!");
			}else{
				model.addAttribute("message","Records updated successfully!!");
			}
			
		}catch(Exception e){
			logger.error("Exception: "+e);
			model.addAttribute("message","Please enter valid Contract Id");
		}
		return showHomePage(request,model);
	}

	/**
	 * Register Executer in BGMS Application
	 * 
	 * @param userID
	 * @param password
	 * @param confirmPassword
	 * @return
	 */
	@RequestMapping(value = "user/executerRegistration.do")
	public ModelAndView executerRegistrationAction(
			@RequestParam(required = true, value = "userId") String userID,
			@RequestParam(required = true, value = "password") String password,
			@RequestParam(required = true, value = "confirmPassword") String confirmPassword,
			@RequestParam(required = true, value = "role") String role,HttpServletRequest request) {

		ModelMap model = new ModelMap();
		/* Checks for Empty String */
		if (userID.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()
				|| role.isEmpty()) {
			model.addAttribute("errorMessage", "Please fill all fields!!");
			return new ModelAndView("registration_executer", "result", model);
		}

		/*
		 * Register Executer in BGMS otherwise displays corresponding error
		 * message.
		 */

		try {
			if (password.equals(confirmPassword)) {
				bgmsService.registerExecuter(userID, password, role);
				model.addAttribute("successMessage", "Registration Successful!");
			} else {
				model.addAttribute("errorMessage",
						"Password doesnot match, Try again!!");
			}
		} catch (Exception e) {
			logger.error(e);
			model.addAttribute("errorMessage",
					"Error Occured, Please try again later!!");
		}
		return new ModelAndView("registration_executer", "result", getCsrfToken(request, model));
	}
	
	/**
	 * Redirect to Contract Registration Page.
	 * 
	 * @return
	 */

	@RequestMapping(value = "user/contractRegistrationPage.html")
	public ModelAndView showContractRegistrationPage(HttpServletRequest request, ModelMap model) {
		if (model == null) {
			model = new ModelMap();
		}

		List<Contractor> contractorList = getAllContractors();
		List<Bank> bankList = getAllBanks();
		model.addAttribute("contractorList", contractorList);
		model.addAttribute("bankList", bankList);
		return new ModelAndView("registration_contract", "result", getCsrfToken(request, model));
	}

	/**
	 * Register Contractor in Application
	 * 
	 * @param contractor
	 * @return
	 */
	@RequestMapping(value = "user/contractorRegistration.do", method = RequestMethod.POST)
	public ModelAndView contractorRegistrationAction(
			@ModelAttribute("contractor") Contractor contractor,HttpServletRequest request) {
		ModelMap model = new ModelMap();

		if (contractor.getContractorName().isEmpty()
				|| contractor.getEmailId().isEmpty()) {
			model.addAttribute("errorMessage",
					"Please fill all mandatory fields..");
		} else {

			try {
				logger.info("Contractor Registration in Queue!!");
				bgmsService.contractorRegistration(contractor);
				model.addAttribute("errorMessage", "Registration Successfull!!");
			} catch (Exception e) {
				logger.error("Exception: " + e.fillInStackTrace());
				model.addAttribute("errorMessage",
						"An Error Occured, please try again later!!");
			}
		}
		return new ModelAndView("registration_contractor", "result", getCsrfToken(request, model));
	}

	/**
	 * Register Bank-Branches in Application
	 * 
	 * @param bank
	 * @return
	 */
	@RequestMapping(value = "user/bankRegistration.do", method = RequestMethod.POST)
	public ModelAndView bankRegistrationAction(@ModelAttribute("bank") Bank bank,HttpServletRequest request) {
		ModelMap model = new ModelMap();
		if (bank.getBankName().isEmpty() || bank.getBranch().isEmpty()
				|| bank.getIfscNumber().isEmpty() || bank.getCity().isEmpty()
				|| bank.getState().isEmpty()) {
			model.addAttribute("errorMessage",
					"Please fill all mandatory fields!!");
		} else {

			try {
				logger.info("Bank Registration in Queue!!");
				bgmsService.bankRegistration(bank);
				model.addAttribute("errorMessage", "Registration Successfull!!");
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
				model.addAttribute("errorMessage",
						"An Error Occured, please try again later!!");
			}
		}
		return new ModelAndView("registration_bank", "result", getCsrfToken(request, model));
	}
	/**
	 * AutoComplete Contractor Field based on letter( starts from 3rd letter input)
	 * @param request
	 * @return
	 */
	@RequestMapping(value="user/getContractorListBasedOnSearch.json", method=RequestMethod.GET)
	@ResponseBody
	public List<String> getContractorListBasedOnSearch(HttpServletRequest request){
		logger.info("Preparing List of Contractors Name");
		String q = request.getParameter("q");
		List<String> listOfContractorNames = new ArrayList<String>();
		List<Contractor> contractorList = bgmsService.getAllContractorBasedOnName(q);
		for(Contractor contractor: contractorList){
			listOfContractorNames.add(contractor.getContractorName().toUpperCase());
		}
		return listOfContractorNames;
	}

	@RequestMapping(value = "user/contractRegistration.do", method = RequestMethod.POST)
	public ModelAndView contractRegistrationAction(
			@RequestParam(value = "contractId") String contractId,
			@RequestParam(value = "contractorName") String contractorName,
			@RequestParam(value = "bankId") String bankId,
			@RequestParam(value = "gaurateeAmount") String guaranteeAmount,
			HttpServletRequest request) {
		
		ModelMap model = new ModelMap();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT+5.30"));
		Date validFrom = null;
		Date validUpto = null;

		if (contractId.isEmpty() || contractorName.isEmpty() || bankId.isEmpty() || guaranteeAmount.isEmpty()) {
			model.addAttribute("message","please fill Mandatory fields!!");
		} else {

			logger.info("Contract Registrion in queue!!");
			try {		
				validFrom = sdf.parse(request.getParameter("validFrom"));
			    validUpto = sdf.parse(request.getParameter("validUpto"));
			    
				if (bgmsService.contractRegistration(contractId,contractorName, Long.parseLong(bankId),guaranteeAmount, validFrom, validUpto)) {
					model.addAttribute("message", "Registration Successfull!!");
				} else {
					logger.info("Error in Contractor and Bank Details Matching!!");
					model.addAttribute("message",
							"Wrong Contract or Bank Details!!");
				}
			} catch (Exception e) {
				model.addAttribute("message",
						"Error Occured, please try again later..");
				e.printStackTrace();
				logger.error("Exception: " + e.getLocalizedMessage());
			}
		}
		/* Returning to contractRegistrationPage.html */
		return showContractRegistrationPage(request, model);
	}
	
	/**
	 * Method to calculate Validity Extension and encashmentDate.
	 * @param fromDate
	 * @param numberOfDays
	 * @return
	 */
	public Date addDateThroughGregorianCalender(Date fromDate,int numberOfDays){
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(fromDate);
		cal.add(Calendar.DAY_OF_MONTH, numberOfDays);
		return cal.getTime();
	}
	
	

}
