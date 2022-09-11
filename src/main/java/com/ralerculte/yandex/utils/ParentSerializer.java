package com.ralerculte.yandex.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.ralerculte.yandex.models.SystemItem;

import java.io.IOException;

public class ParentSerializer extends JsonSerializer<SystemItem> {

    @Override
    public void serialize(SystemItem value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(value.getId());
    }
}
