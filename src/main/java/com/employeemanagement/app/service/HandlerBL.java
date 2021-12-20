package com.employeemanagement.app.service;

import org.springframework.stereotype.Service;

import com.employeemanagement.app.helpers.ApiRes;
import com.employeemanagement.app.helpers.ValidationHelper;

@Service
public class HandlerBL {

	public ApiRes<Object> translateError(ValidationHelper.Result result, String suggestion) {
		String error = "";
		switch (result) {

		case login_fail:

			error = "Đăng nhập thất bại";

		default:
			break;
		}
		ApiRes<Object> apiRes = new ApiRes<Object>();
		apiRes.setErrorCode("mvvn4m");
		apiRes.setError(true);
		apiRes.setErrorReason(result.toString());
		if (suggestion == null || suggestion.isEmpty())
			apiRes.setToastMessage(error);
		else
			apiRes.setToastMessage(error + " [" + suggestion + "]");
		return apiRes;
	}

}
