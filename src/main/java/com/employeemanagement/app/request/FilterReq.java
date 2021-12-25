package com.employeemanagement.app.request;

public class FilterReq {

	private String Keyword;
	private Integer PageIndex;
	private Integer PageSize;
	private String LocaleCode;

	public String getLocaleCode() {
		return LocaleCode;
	}

	public void setLocaleCode(String localeCode) {
		LocaleCode = localeCode;
	}

	public String getKeyword() {
		return Keyword;
	}

	public void setKeyword(String keyword) {
		Keyword = keyword;
	}

	public Integer getPageIndex() {
		return PageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		PageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return PageSize;
	}

	public void setPageSize(Integer pageSize) {
		PageSize = pageSize;
	}
}
