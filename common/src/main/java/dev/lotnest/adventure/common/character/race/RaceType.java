package dev.lotnest.adventure.common.character.race;

import dev.lotnest.adventure.common.character.ability.Abilities;
import dev.lotnest.adventure.common.character.ability.Ability;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@Getter
public enum RaceType {

    HUMAN(ChatColor.YELLOW, UnlockType.DEFAULT, Set.of(Abilities.OVERKILL)),
    ELF(ChatColor.GREEN, UnlockType.DEFAULT, Collections.emptySet()),
    DWARF(ChatColor.GOLD, UnlockType.DEFAULT, Collections.emptySet()),
    DRAGONOID(ChatColor.RED, UnlockType.DEFAULT, Collections.emptySet()),
    WITCH(ChatColor.DARK_PURPLE, UnlockType.DEFAULT, Collections.emptySet()),
    MANBEAST(ChatColor.LIGHT_PURPLE, UnlockType.SHOP, Collections.emptySet()),
    MERFOLK(ChatColor.DARK_AQUA, UnlockType.QUEST, Collections.emptySet());

    @NotNull
    private final ChatColor color;

    @NotNull
    private final UnlockType unlockType;

    @NotNull
    private final Set<Ability> abilities;

    @NotNull
    public String getDisplayName() {
        return color + StringUtils.capitalize(name().toLowerCase());
    }

    public enum UnlockType {

        DEFAULT,
        SHOP,
        QUEST

    }

}
