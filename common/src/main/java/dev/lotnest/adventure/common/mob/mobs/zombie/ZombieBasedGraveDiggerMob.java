package dev.lotnest.adventure.common.mob.mobs.zombie;

import dev.lotnest.adventure.common.item.Items;
import dev.lotnest.adventure.common.mob.ZombieBasedMob;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ZombieBasedGraveDiggerMob extends ZombieBasedMob {

    private static final ItemStack IRON_SHOVEL = new ItemStack(net.minecraft.world.item.Items.IRON_SHOVEL);

    private static final ItemStack LEATHER_HELMET = new ItemStack(net.minecraft.world.item.Items.LEATHER_HELMET);
    private static final ItemStack LEATHER_CHESTPLATE = new ItemStack(net.minecraft.world.item.Items.LEATHER_CHESTPLATE);
    private static final ItemStack LEATHER_LEGGINGS = new ItemStack(net.minecraft.world.item.Items.LEATHER_LEGGINGS);
    private static final ItemStack LEATHER_BOOTS = new ItemStack(net.minecraft.world.item.Items.LEATHER_BOOTS);

    public ZombieBasedGraveDiggerMob(@NotNull Location spawnLocation) {
        super(spawnLocation, "Grave Digger", 1);
        setItemInHand(InteractionHand.MAIN_HAND, IRON_SHOVEL.copy());
        setItemSlot(EquipmentSlot.HEAD, LEATHER_HELMET.copy());
        setItemSlot(EquipmentSlot.CHEST, LEATHER_CHESTPLATE.copy());
        setItemSlot(EquipmentSlot.LEGS, LEATHER_LEGGINGS.copy());
        setItemSlot(EquipmentSlot.FEET, LEATHER_BOOTS.copy());
    }

    @Override
    @NotNull
    public List<org.bukkit.inventory.ItemStack> getDrops() {
        return Collections.singletonList(Items.ZOMBIE_BRAIN);
    }

}
