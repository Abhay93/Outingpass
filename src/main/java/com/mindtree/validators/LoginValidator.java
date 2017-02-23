package com.mindtree.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mindtree.dto.LoginForm;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(LoginForm.class);
	}

	@Override
	public void validate(Object object, Errors errors) {
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username",
				"error.username.required", "Fill all details");
		
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password",
				"error.password.required", "Fill all details");
	}

}
