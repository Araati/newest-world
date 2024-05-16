package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.ModelParameter;
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


    public Map<String, String> validateAndInsertDefaultIfRequired(final Map<String, String> input,
                                                                  final List<ModelParameter> expectedParameters) {
        Map<String, String> parameters = new HashMap<>();
        for (final ModelParameter expectedParameter : expectedParameters) {

            Set<ConstraintViolation<ModelParameter>> violations;
            ModelParameter assembledParameter = expectedParameter;

            if (input.containsKey(expectedParameter.getName()))
                assembledParameter = assembledParameter.withData(input.get(expectedParameter.getName())); // take data from input
            else if (expectedParameter.getData() != null)
                assembledParameter = assembledParameter.withData(expectedParameter.getData()); // take default data from structure
            else if (expectedParameter.isRequired()) // if required and no data, then exception
                throw new ValidationFailedException("Input parameter not present: " + expectedParameter.getName());

            violations = validator.validate(assembledParameter);

            if (!violations.isEmpty())
                throw new ValidationFailedException();

            parameters.put(expectedParameter.getName(), assembledParameter.getData());
        }
        return parameters;
    }
}
