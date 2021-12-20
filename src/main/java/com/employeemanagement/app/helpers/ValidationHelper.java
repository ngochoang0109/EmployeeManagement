package com.employeemanagement.app.helpers;

import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ValidationHelper {

	public enum Result {
		valid, login_fail, duplicate_code, duplicate_variant, empty_code, invalid_code
	}

	public static Boolean isNullorEmpty(String str) {
		if (str == null || str.trim().isEmpty())
			return true;
		return false;
	}

	public static Boolean isNullValue(Object value) {
		if (value == null)
			return true;
		if (value instanceof String) {
			if (value.toString().trim().isEmpty())
				return true;
		}
		return false;
	}

	public static Result validateCode(String code) {
		if (code == null || code.trim().isEmpty())
			return Result.empty_code;
		if (!Pattern.matches("[a-zA-Z_0-9]+", code))
			return Result.invalid_code;
		return Result.valid;
	}
}
