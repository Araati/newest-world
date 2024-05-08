package com.newestworld.content.service;

import com.newestworld.commons.exception.ValidationFailedException;
import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.dao.StructureParameterRepository;
import com.newestworld.content.dto.StructureParameterCreateDTO;
import com.newestworld.content.dto.StructureParameterDTO;
import com.newestworld.content.model.entity.StructureParameterEntity;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StructureParameterService {

    private final Validator validator;
    private final StructureParameterRepository structureParameterRepository;

    public List<StructureParameterDTO> create(final long actionId, final List<StructureParameterCreateDTO> request) {
        List<StructureParameterDTO> parameters = new ArrayList<>();
        for (final StructureParameterCreateDTO structureParameterCreateDTO : request) {
            structureParameterRepository.save(new StructureParameterEntity(actionId, structureParameterCreateDTO));
            parameters.add(new StructureParameterDTO(
                    structureParameterCreateDTO.getName(),
                    structureParameterCreateDTO.isRequired(),
                    structureParameterCreateDTO.getType(),
                    structureParameterCreateDTO.getInit(),
                    structureParameterCreateDTO.getMin(),
                    structureParameterCreateDTO.getMax()));
        }
        return parameters;
    }

    public void delete(final long modelId) {
        structureParameterRepository.saveAll(structureParameterRepository.findAllByStructureIdAndDeletedIsFalse(modelId).stream()
                .map(x -> x.withDeleted(true)).collect(Collectors.toList()));
    }

    public void deleteAll(final List<Long> ids) {
        structureParameterRepository.saveAll(structureParameterRepository.findAllByStructureIdInAndDeletedIsFalse(ids).stream()
                .map(x -> x.withDeleted(true)).toList());
    }

    public List<StructureParameter> findById(final long modelId)   {
        return new ArrayList<>(structureParameterRepository.findAllByStructureIdAndDeletedIsFalse(modelId)
                .stream().map(StructureParameterDTO::new).toList());
    }

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
