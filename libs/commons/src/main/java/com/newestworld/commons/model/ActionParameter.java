package com.newestworld.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActionParameter implements ActionIdReference {

    private long actionId;
    private String name;
    private Object value;

}
