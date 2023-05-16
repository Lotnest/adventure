package dev.lotnest.adventure.common;

import dev.lotnest.adventure.common.texture.SkinTexture;
import dev.lotnest.adventure.common.texture.SkinTextures;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum CharacterType {

    THORNE(SkinTextures.CHARACTER_THORNE, ChatColor.GOLD.toString() + ChatColor.BOLD + "Thorne");

    @NotNull
    private final SkinTexture skinTexture;

    @NotNull
    private final String name;

}
