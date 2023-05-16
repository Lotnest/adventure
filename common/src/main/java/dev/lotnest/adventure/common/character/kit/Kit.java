package dev.lotnest.adventure.common.character.kit;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public record Kit(@NotNull String id, @NotNull ItemStack[] armor, @NotNull ItemStack[] items) {

    public void apply(@NotNull Player player) {
        player.getInventory().setContents(items);
        player.getInventory().setArmorContents(armor);
        player.updateInventory();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Kit kit = (Kit) o;

        return new EqualsBuilder()
                .append(id, kit.id)
                .append(armor, kit.armor)
                .append(items, kit.items)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(armor)
                .append(items)
                .toHashCode();
    }

    @Override
    @NotNull
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("armor", Arrays.toString(armor))
                .append("items", Arrays.toString(items))
                .toString();
    }
}
