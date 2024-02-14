package com.newestworld.commons.model;

import java.time.LocalDateTime;
import java.util.List;

public interface CompoundActionStructure {

    long getId();

    String getName();

    List<String> getInput();

    List<BasicAction> getSteps();

    LocalDateTime getCreatedAt();

}
