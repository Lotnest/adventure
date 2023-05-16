package dev.lotnest.adventure.common.texture;

import com.mojang.authlib.properties.Property;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public record SkinTexture(@NotNull String value, @NotNull String signature) {

    @Contract(value = " -> new", pure = true)
    @NotNull
    public Property asProperty() {
        return new Property("textures", value, signature);
    }

}
