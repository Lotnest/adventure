package dev.lotnest.adventure.common.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import dev.lotnest.adventure.common.character.ability.AbilityHandler;
import dev.lotnest.adventure.common.deserializer.AbilityHandlerDeserializer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

@RequiredArgsConstructor
@Getter
public class JsonParser<T> {

    protected static final ObjectMapper MAPPER = new ObjectMapper()
            .registerModule(new SimpleModule().addDeserializer(AbilityHandler.class, new AbilityHandlerDeserializer()));

    @NotNull
    private final Class<T> clazz;

    @SneakyThrows
    public T parse(@NotNull String json) {
        return MAPPER.readValue(json, clazz);
    }

    @SneakyThrows
    public T parseFile(@NotNull InputStream fileInputStream) {
        return MAPPER.readValue(fileInputStream, clazz);
    }

    @SneakyThrows
    public String toJson(@NotNull T object) {
        return MAPPER.writeValueAsString(object);
    }

}
