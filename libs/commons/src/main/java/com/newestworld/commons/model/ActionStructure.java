package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.List;

public interface ActionStructure {

    long getId();

    String getName();

    ModelParameters getParameters();

    List<Node> getSteps();

    LocalDateTime getCreatedAt();

}