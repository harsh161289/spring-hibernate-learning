package com.anksys.bgms.model;

public enum BGMSRole{
	BGMS_ADMIN("BGMS_ADMIN"),BGMS_EXECUTER("BGMS_EXECUTER");
	
	private String value;
	
	private BGMSRole(String value){
		this.value = value;
	}
	
	public String getRole(){
		return value;
	}
	
}