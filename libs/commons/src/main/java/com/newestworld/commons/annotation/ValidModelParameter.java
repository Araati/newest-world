package com.newestworld.commons.annotation;

import com.newestworld.commons.util.validator.ModelParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ModelParameterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidModelParameter {
    String message() default "ModelParameter invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
