package com.newestworld.content.service;

import com.newestworld.commons.model.ModelParameters;
import com.newestworld.commons.model.Node;
import com.newestworld.content.dao.NodeRepository;
import com.newestworld.content.dto.NodeCreateDTO;
import com.newestworld.content.dto.NodeDTO;
import com.newestworld.content.model.entity.NodeEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NodeService {

    private final ModelParameterService modelParameterService;
    private final NodeRepository nodeRepository;

    public List<Node> createAll(final long actionId, final List<NodeCreateDTO> request) {
        List<Node> nodeDTOS = new ArrayList<>();
        for (NodeCreateDTO nodeCreateDTO : request) {
            NodeEntity nodeEntity = new NodeEntity(actionId, nodeCreateDTO);
            nodeRepository.save(nodeEntity);
            ModelParameters modelParameters = modelParameterService.create(nodeEntity.getId(), nodeCreateDTO.getParams());
            nodeDTOS.add(new NodeDTO(nodeEntity, modelParameters));
        }
        return nodeDTOS;
    }

    public void deleteAll(final long actionId) {
        List<NodeEntity> nodeEntities = nodeRepository.findAllByStructureIdAndDeletedIsFalse(actionId);
        for (NodeEntity nodeEntity : nodeEntities) {
            nodeRepository.save(nodeEntity.withDeleted(true));
            modelParameterService.delete(actionId);
        }
    }

    public List<Node> findAllById(final long actionId)  {
        List<NodeEntity> nodeEntities = nodeRepository.findAllByStructureIdAndDeletedIsFalse(actionId);
        List<Node> nodes = new ArrayList<>();
        for (NodeEntity nodeEntity : nodeEntities) {
            nodes.add(new NodeDTO(nodeEntity, modelParameterService.findById(nodeEntity.getId())));
        }
        return nodes;
    }
}
