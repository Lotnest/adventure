package dev.lotnest.adventure.common.mob;

import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Zombie;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Getter
public class ZombieBasedMob extends Zombie implements Mob {

    private final Location mobSpawnLocation;
    private final String mobName;
    private final int mobLevel;
    private final float mobBaseHealth;

    public ZombieBasedMob(@NotNull Location mobSpawnLocation, @NotNull String mobName, int mobLevel) {
        this(mobSpawnLocation, mobName, mobLevel, 20F);
    }

    public ZombieBasedMob(@NotNull Location mobSpawnLocation, @NotNull String mobName, int mobLevel, float mobBaseHealth) {
        super(EntityType.ZOMBIE, ((CraftWorld) requireNonNull(mobSpawnLocation.getWorld())).getHandle());
        this.mobSpawnLocation = mobSpawnLocation;
        this.mobName = mobName;
        this.mobLevel = mobLevel;
        this.mobBaseHealth = mobBaseHealth;

        setCustomName(Component.literal(Mob.getFormattedName(false, mobName, mobLevel, mobBaseHealth)));
        setPos(mobSpawnLocation.getX(), mobSpawnLocation.getY(), mobSpawnLocation.getZ());
        setHealth(mobBaseHealth);
        setAggressive(true);
        setBaby(false);
        setCanPickUpLoot(false);
        setCustomNameVisible(true);
        setInvisible(false);
        addTag("adventureMob");

        Mob.summon(this, (CraftWorld) mobSpawnLocation.getWorld());
    }

    @Override
    public void updateMobName() {
        setCustomName(Component.literal(Mob.getFormattedName(false, mobName, mobLevel, getHealth())));
    }

    @Override
    public @NotNull List<ItemStack> getDrops() {
        return Collections.emptyList();
    }

}
