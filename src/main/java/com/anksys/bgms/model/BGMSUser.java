package com.anksys.bgms.model;

public class BGMSUser {

	private int id;

	private String userName;

	private String password;

	private boolean enabled;

	private BGMSRole role;

	public BGMSUser() {
		super();
	}

	public BGMSUser(String userName, String password, BGMSRole role) {
		super();
		this.userName = userName.toUpperCase();
		this.password = password;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName.toUpperCase();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public BGMSRole getRole() {
		return role;
	}

	public void setRole(BGMSRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "BGMSUser [id=" + id + ", userName=" + userName + ", password="
				+ password + ", enabled=" + enabled + ", role=" + role + "]";
	}

}
