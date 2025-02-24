package com.hannsoftware.itms.app.enums;

public enum Role {
	
        EMPLOYEE(0, "Employee"), IT_SUPPORT(1, "IT Support");
	
	private Integer code;
	
	private String description;
	
	private Role(Integer code, String description) {
		this.code = code;
		this.description = description;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static Role toEnum(Integer code) {
		if(code == null) return null;
		for(Role role: Role.values()) {
			if(code.equals(role)) {
				return role;
			}
		}
		throw new IllegalArgumentException("Invalid Role!");
	}

}
