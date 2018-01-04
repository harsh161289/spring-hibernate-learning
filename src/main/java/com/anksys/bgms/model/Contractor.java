package com.anksys.bgms.model;

import java.util.Set;

public class Contractor {

	private long contractorId;
	private String contractorName;
	private String emailId;
	private String contactNumber;
	private String alternateNumber;
	private String address;
	private String city;
	private String state;
	private Set<Contract> contracts;

	public Contractor() {
		super();
	}

	public long getContractorId() {
		return contractorId;
	}

	public void setContractorId(long contractorId) {
		this.contractorId = contractorId;
	}

	public String getContractorName() {
		return contractorName;
	}

	public void setContractorName(String contractorName) {
		this.contractorName = contractorName.toUpperCase();
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId.toUpperCase();
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getAlternateNumber() {
		return alternateNumber;
	}

	public void setAlternateNumber(String alternateNumber) {
		this.alternateNumber = alternateNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address.toUpperCase();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.toUpperCase();
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state.toUpperCase();
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	@Override
	public String toString() {
		return "Contractor [id=" + contractorId + ", contratorName="
				+ contractorName + ", emailId=" + emailId + ", contactNumber="
				+ contactNumber + ", alternateNumber=" + alternateNumber
				+ ", address=" + address + ", city=" + city + ", state="
				+ state + "]";
	}

}
