package com.newestworld.content.service;

import com.newestworld.commons.model.ModelParameter;
import com.newestworld.commons.model.ModelParameters;
import com.newestworld.content.dao.ModelParameterRepository;
import com.newestworld.content.dto.ModelParameterCreateDTO;
import com.newestworld.content.model.entity.ModelParameterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ModelParameterService {

    private final ModelParameterRepository modelParameterRepository;

    public ModelParameters create(final long actionId, final List<ModelParameterCreateDTO> request) {
        List<ModelParameter> parameters = new ArrayList<>();
        for (final ModelParameterCreateDTO modelParameterCreateDTO : request) {
            modelParameterRepository.save(new ModelParameterEntity(actionId, modelParameterCreateDTO));
            parameters.add(new ModelParameter(actionId,
                    modelParameterCreateDTO.getName(),
                    modelParameterCreateDTO.isRequired(),
                    modelParameterCreateDTO.getData(),
                    modelParameterCreateDTO.getType(),
                    modelParameterCreateDTO.getInit(),
                    modelParameterCreateDTO.getMin(),
                    modelParameterCreateDTO.getMax()));
        }
        return new ModelParameters.Impl(parameters);
    }

    public void delete(final long modelId) {
        modelParameterRepository.saveAll(modelParameterRepository.findAllByModelIdAndDeletedIsFalse(modelId).stream()
                .map(x -> x.withDeleted(true)).collect(Collectors.toList()));
    }

    public void deleteAll(final List<Long> ids) {
        modelParameterRepository.saveAll(modelParameterRepository.findAllByModelIdInAndDeletedIsFalse(ids).stream()
                .map(x -> x.withDeleted(true)).toList());
    }

    public ModelParameters findById(final long modelId)   {
        List<ModelParameterEntity> actionParamsEntities = modelParameterRepository.findAllByModelIdAndDeletedIsFalse(modelId);
        List<ModelParameter> modelParameterList = new ArrayList<>();
        for (ModelParameterEntity source : actionParamsEntities) {
            modelParameterList.add(new ModelParameter(modelId,
                    source.getName(),
                    source.isRequired(),
                    source.getData(),
                    source.getType(),
                    source.getInit(),
                    source.getMin(),
                    source.getMax()));
        }
        return new ModelParameters.Impl(modelParameterList);
    }
}
