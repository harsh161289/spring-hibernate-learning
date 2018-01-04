package com.anksys.bgms.model;

import java.util.Set;

public class Bank {

	private long bankId;
	private String bankName;
	private String branch;
	private String city;
	private String State;
	private String ifscNumber;

	private Set<Contract> contracts;

	public Bank() {
		super();
	}

	public long getBankId() {
		return bankId;
	}

	public void setBankId(long bankId) {
		this.bankId = bankId;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName.toUpperCase();
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch.toUpperCase();
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.toUpperCase();
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state.toUpperCase();
	}

	public String getIfscNumber() {
		return ifscNumber;
	}

	public void setIfscNumber(String ifscCode) {
		this.ifscNumber = ifscCode.toUpperCase();
	}

	public void setContracts(Set<Contract> contracts) {
		this.contracts = contracts;
	}

	public Set<Contract> getContracts() {
		return contracts;
	}

	@Override
	public String toString() {
		return "Bank [bankName=" + bankName + ", branch=" + branch + ", city="
				+ city + ", State=" + State + ", ifscNumber=" + ifscNumber
				+ "]";
	}

}
