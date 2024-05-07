package com.newestworld.content.service;

import com.newestworld.commons.model.StructureParameter;
import com.newestworld.content.dao.StructureParameterRepository;
import com.newestworld.content.dto.StructureParameterCreateDTO;
import com.newestworld.content.dto.StructureParameterDTO;
import com.newestworld.content.model.entity.StructureParameterEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StructureParameterService {

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
}
