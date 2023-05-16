package dev.lotnest.adventure.common.texture;

import com.mojang.authlib.GameProfile;
import dev.lotnest.adventure.common.util.EntityUtil;
import lombok.Getter;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Getter
public class ClientboundTexturedServerPlayer {

    @NotNull
    private final SkinTexture skinTexture;

    @NotNull
    private final String name;

    @NotNull
    private final Player receivingPlayer;

    @NotNull
    private final ServerPlayer receivingNmsPlayer;

    @Nullable
    private ServerPlayer result;

    @Nullable
    private Player resultBukkit;

    public ClientboundTexturedServerPlayer(@NotNull SkinTexture skinTexture, @NotNull String name, @NotNull Player receivingPlayer) {
        this.skinTexture = skinTexture;
        this.name = name;
        this.receivingPlayer = receivingPlayer;
        receivingNmsPlayer = ((CraftPlayer) receivingPlayer).getHandle();
    }

    public void spawn(@NotNull Location location) {
        if (result != null) {
            return;
        }

        GameProfile resultGameProfile = new GameProfile(UUID.randomUUID(), name);
        resultGameProfile.getProperties().put("textures", skinTexture.asProperty());

        Level playerLevel = receivingNmsPlayer.getLevel();

        result = new ServerPlayer(playerLevel.getServer(), playerLevel.getMinecraftWorld(), resultGameProfile);
        resultBukkit = result.getBukkitEntity();
        result.connection = EntityUtil.newClientboundServerGamePacketListener(result.getBukkitEntity());
        result.forceSetPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
        result.setInvulnerable(true);
        result.setSilent(true);

        EntityUtil.spawnClientboundServerPlayer(receivingNmsPlayer, result);
    }

    public void remove() {
        if (result == null) {
            return;
        }
        EntityUtil.removeClientboundServerPlayer(receivingNmsPlayer, result);
    }

    public void teleport(@NotNull Location location) {
        if (resultBukkit == null || result == null) {
            return;
        }

        EntityUtil.teleportClientboundEntity(receivingNmsPlayer, result.getBukkitEntity(), location);
    }

}
