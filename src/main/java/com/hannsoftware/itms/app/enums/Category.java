package com.hannsoftware.itms.app.enums;

public enum Category {
	
        NETWORK(0, "Network"), HARDWARE(1, "Hardware"), SOFTWARE(2, "Software"), OTHER(3, "Other");
	
	private Integer code;
	
	private String description;
	
	private Category(Integer code, String description) {
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

	public static Category toEnum(Integer code) {
		if(code == null) return null;
		for(Category category: Category.values()) {
			if(code.equals(category)) {
				return category;
			}
		}
		throw new IllegalArgumentException("Invalid Category!");
	}

}
