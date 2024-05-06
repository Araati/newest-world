package com.newestworld.commons.util.validator;

import com.newestworld.commons.annotation.ValidModelParameter;
import com.newestworld.commons.model.ModelParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModelParameterValidator implements ConstraintValidator<ValidModelParameter, String> {


    @Override
    public void initialize(ValidModelParameter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }


}
