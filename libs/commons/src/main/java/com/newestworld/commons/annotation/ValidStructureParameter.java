package com.newestworld.commons.annotation;

import com.newestworld.commons.validation.StructureParameterValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = StructureParameterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidStructureParameter {
    String message() default "StructureParameter invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
