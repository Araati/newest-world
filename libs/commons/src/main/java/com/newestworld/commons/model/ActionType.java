package com.newestworld.commons.model;

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

    START(1),
    END(2),
    MODIFY(3),
    CREATE_ACTION(5),
    CREATE_ABSTRACT_OBJECT(10);



    private static final Map<Integer, ActionType> TYPE_MAP;

    static {
        TYPE_MAP = Arrays.stream(ActionType.values())
                .collect(Collectors.toMap(ActionType::getId, Function.identity()));
    }

    private int id;

    @NonNull
    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static ActionType decode(final String type){
        return decode(Integer.parseInt(type));
    }

    public static ActionType decode(final int type){
        if(!TYPE_MAP.containsKey(type)){
            throw new RuntimeException("ActionType not found");
        }
        return TYPE_MAP.get(type);
    }

    @JsonValue
    public int encode(){
        return id;
    }
}
