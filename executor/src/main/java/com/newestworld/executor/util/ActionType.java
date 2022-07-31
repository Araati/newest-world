package com.newestworld.executor.util;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ActionType {

    CREATE(1),
    START(2),
    ADD(3);



    private static final Map<Integer, ActionType> TYPE_MAP;

    static {
        TYPE_MAP = Arrays.stream(ActionType.values())
                .collect(Collectors.toMap(ActionType::getType, Function.identity()));
    }

    private int type;

    @NonNull
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ActionType fromValue(final String type){
        return fromValue(Integer.parseInt(type));
    }

    public static ActionType fromValue(final int type){
        if(!TYPE_MAP.containsKey(type)){
            throw new RuntimeException("ActionType not found");
        }
        return TYPE_MAP.get(type);
    }

    @JsonValue
    public int toValue(){
        return type;
    }
}
