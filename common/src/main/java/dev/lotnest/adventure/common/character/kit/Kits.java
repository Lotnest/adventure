package dev.lotnest.adventure.common.character.kit;

import com.google.common.collect.Maps;
import dev.lotnest.adventure.common.character.kit.factory.HumanKitFactory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

public class Kits {

    public static final Kit HUMAN = HumanKitFactory.createKit();

    private static final Set<Kit> ALL = Set.of(HUMAN);

    private static final ConcurrentMap<ItemStack, Boolean> IS_ITEM_KIT = Maps.newConcurrentMap();
    private static final ConcurrentMap<ItemStack, Boolean> IS_ITEM_WITH_ABILITY = Maps.newConcurrentMap();

    private Kits() {
    }

    @NotNull
    public static Optional<Kit> findKitById(@NotNull String id) {
        return ALL.stream()
                .filter(kit -> kit.id().equals(id))
                .findFirst();
    }

    public static boolean isItemKit(@NotNull ItemStack item) {
        return IS_ITEM_KIT.computeIfAbsent(item, key -> ALL.stream()
                .anyMatch(kit -> Arrays.asList(kit.items()).contains(item) || Arrays.asList(kit.armor()).contains(item)));
    }

    public static boolean isItemWithAbility(@NotNull ItemStack item) {
        return IS_ITEM_WITH_ABILITY.computeIfAbsent(item, key -> ALL.stream()
                .anyMatch(kit -> kit.items().length > 0 && kit.items()[0].equals(item)));
    }
}
