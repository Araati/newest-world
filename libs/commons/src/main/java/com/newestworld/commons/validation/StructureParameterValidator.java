package com.newestworld.commons.validation;

import com.newestworld.commons.annotation.ValidStructureParameter;
import com.newestworld.commons.model.StructureParameter;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StructureParameterValidator implements ConstraintValidator<ValidStructureParameter, StructureParameter> {


    @Override
    public void initialize(ValidStructureParameter constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(StructureParameter parameter, ConstraintValidatorContext constraintValidatorContext) {
        return isBoundsCorrect(parameter)
                && isDataValidAndInBounds(parameter.getInit(), parameter);
    }

    private boolean isDataValidAndInBounds(final String data, StructureParameter parameter) {
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

    private boolean isBoundsCorrect(final StructureParameter parameter) {
        if (parameter.getMin() == null || parameter.getMax() == null)   {
            return true;
        } else return parameter.getMin() < parameter.getMax();
    }
}
