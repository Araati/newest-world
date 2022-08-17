package com.newestworld.commons.util.converter;

import com.newestworld.commons.model.ActionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class ActionTypeConverter implements AttributeConverter<ActionType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(final ActionType data) {
        return data == null ? null : data.getId();
    }

    @Override
    public ActionType convertToEntityAttribute(final Integer data) {
        return data == null ? null : ActionType.decode(data);
    }
}
