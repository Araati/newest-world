package com.newestworld.commons.model;

public interface StructureParameter {

    String getName();
    boolean isRequired();
    String getType();
    String getInit();
    Long getMax();
    Long getMin();

}
