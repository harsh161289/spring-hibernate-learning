package com.anksys.bgms.model;

import java.util.Date;

public class Contract {
	private String contractId;
	private String gaurateeAmount;
	private Date validFrom;
	private Date validUpto;
	private Date ExtendedUpto;
	private Date extensionNoticeDate;
	private Date encashmentNoticeDate;

	private Bank bank;
	private Contractor contractor;

	public Contract() {
		super();
	}

	public Contract(String contractId, String gaurateeAmount, Date validFrom,
			Date validUpto) {
		super();
		this.contractId = contractId;
		this.gaurateeAmount = gaurateeAmount;
		this.validFrom = validFrom;
		this.validUpto = validUpto;
	}

	public String getContractId() {
		return contractId;
	}

	public void setContractId(String contractId) {
		this.contractId = contractId;
	}

	public String getGaurateeAmount() {
		return gaurateeAmount;
	}

	public void setGaurateeAmount(String gaurateeAmount) {
		this.gaurateeAmount = gaurateeAmount;
	}

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public Date getValidUpto() {
		return validUpto;
	}

	public void setValidUpto(Date validUpto) {
		this.validUpto = validUpto;
	}

	public Date getExtendedUpto() {
		return ExtendedUpto;
	}

	public void setExtendedUpto(Date extendedUpto) {
		ExtendedUpto = extendedUpto;
	}

	public Bank getBank() {
		return bank;
	}

	public void setBank(Bank bank) {
		this.bank = bank;
	}

	public Contractor getContractor() {
		return contractor;
	}

	public void setContractor(Contractor contractor) {
		this.contractor = contractor;
	}

 

	public Date getExtensionNoticeDate() {
		return extensionNoticeDate;
	}

	public void setExtensionNoticeDate(Date extensionNoticeDate) {
		this.extensionNoticeDate = extensionNoticeDate;
	}

	public Date getEncashmentNoticeDate() {
		return encashmentNoticeDate;
	}

	public void setEncashmentNoticeDate(Date encashmentNoticeDate) {
		this.encashmentNoticeDate = encashmentNoticeDate;
	}

	@Override
	public String toString() {
		return "Contract [contractId=" + contractId + ", gaurateeAmount="
				+ gaurateeAmount + ", validFrom=" + validFrom + ", validUpto="
				+ validUpto + ", ExtendedUpto=" + ExtendedUpto
				+ ", extensionNoticeSentDate=" + extensionNoticeDate
				+ ", encashmentNoticeSentDate=" + encashmentNoticeDate
				+ ", bank=" + bank + ", contractor=" + contractor + "]";
	}

}
