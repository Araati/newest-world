package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.StructureParameter;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class StructureParameterValidationService {

    private final Validator validator;


    public Map<String, String> validateAndInsertDefaultIfRequired(final Map<String, String> input, final List<StructureParameter> expectedParameters) {
        Map<String, String> parameters = new HashMap<>();
        for (final StructureParameter expectedParameter : expectedParameters) {

            Set<ConstraintViolation<ModelParameter>> violations;
            var validatableBuilder = ModelParameter.builder();

            validatableBuilder
                    .name(expectedParameter.getName())
                    .required(expectedParameter.isRequired())
                    .type(expectedParameter.getType())
                    .min(expectedParameter.getMin())
                    .max(expectedParameter.getMax());

            if (input.containsKey(expectedParameter.getName()))
                validatableBuilder.data(input.get(expectedParameter.getName()));
            else if (expectedParameter.getInit() != null)
                validatableBuilder.data(expectedParameter.getInit());
            else if (expectedParameter.isRequired())
                throw new ValidationFailedException("Input parameter not present: " + expectedParameter.getName());

            var validatable = validatableBuilder.build();
            violations = validator.validate(validatableBuilder.build());

            if (!violations.isEmpty())
                throw new ValidationFailedException();

            parameters.put(expectedParameter.getName(), validatable.getData());
        }
        return parameters;
    }
}
