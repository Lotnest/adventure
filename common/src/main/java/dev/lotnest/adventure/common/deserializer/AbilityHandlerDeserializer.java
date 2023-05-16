package dev.lotnest.adventure.common.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import dev.lotnest.adventure.common.character.ability.AbilityHandler;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AbilityHandlerDeserializer extends JsonDeserializer<AbilityHandler> {

    @SneakyThrows
    @Override
    public AbilityHandler deserialize(@NotNull JsonParser jsonParser, @NotNull DeserializationContext deserializationContext) {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String className = node.asText();

        try {
            Class<?> handlerClass = Class.forName(className);
            if (!AbilityHandler.class.isAssignableFrom(handlerClass)) {
                throw new IOException("Class " + handlerClass.getName() + " does not inherit from AbilityHandler");
            }
            return (AbilityHandler) handlerClass.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException |
                 InvocationTargetException exception) {
            throw new IOException("Unable to deserialize AbilityHandler: " + exception.getMessage());
        }
    }

}
