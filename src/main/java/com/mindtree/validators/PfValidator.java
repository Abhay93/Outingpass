package com.mindtree.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mindtree.entity.User;

public class PfValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(User.class);
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userType",
				"error.userType.required", "*");
		*/
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name",
				"error.name.required","*");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userID",
				"error.userID.required","*");
	}

}
