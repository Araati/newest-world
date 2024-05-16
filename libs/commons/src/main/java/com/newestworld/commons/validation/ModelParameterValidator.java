package com.newestworld.commons.validation;

import com.newestworld.commons.annotation.ValidModelParameter;
import com.newestworld.commons.model.ModelParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ModelParameterValidator implements ConstraintValidator<ValidModelParameter, ModelParameter> {


    @Override
    public void initialize(ValidModelParameter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(ModelParameter parameter, ConstraintValidatorContext constraintValidatorContext) {
        return isBoundsCorrect(parameter)
                && isDataValidAndInBounds(parameter.getData(), parameter);
    }

    private boolean isDataValidAndInBounds(final String data, ModelParameter parameter) {
        if (data == null)
            return true;

        //todo enum
        switch (parameter.getType()) {
            case "string":
                if ((parameter.getMin() != null && data.length() < parameter.getMin())
                        || (parameter.getMax() != null && data.length() > parameter.getMax()))
                    return false;
                break;
            case "int":
                try {
                    int parsed = Integer.parseInt(data);
                    if ((parameter.getMin() != null && parsed < parameter.getMin())
                            || (parameter.getMax() != null && parsed > parameter.getMax()))
                        return false;
                } catch (Exception e) {
                    return false;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    private boolean isBoundsCorrect(final ModelParameter parameter) {
        if (parameter.getMin() == null || parameter.getMax() == null)   {
            return true;
        } else return parameter.getMin() < parameter.getMax();
    }
}
