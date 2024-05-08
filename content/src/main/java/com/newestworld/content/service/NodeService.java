package com.newestworld.content.service;

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

    private final NodeRepository nodeRepository;

    public List<Node> createAll(final long actionId, final List<NodeCreateDTO> request) {
        List<Node> nodes = new ArrayList<>();
        for (NodeCreateDTO createDTO : request) {
            NodeEntity entity = new NodeEntity(actionId, createDTO);
            nodeRepository.save(entity);
            nodes.add(new NodeDTO(entity));
        }
        return nodes;
    }

    public void deleteAll(final long actionId) {
        List<NodeEntity> entities = nodeRepository.findAllByStructureIdAndDeletedIsFalse(actionId);
        nodeRepository.saveAll(entities.stream().map(x -> x.withDeleted(true)).toList());
    }

    public List<Node> findAllById(final long actionId)  {
        return new ArrayList<>(
                nodeRepository.findAllByStructureIdAndDeletedIsFalse(actionId).stream().map(NodeDTO::new).toList()
        );
    }
}
