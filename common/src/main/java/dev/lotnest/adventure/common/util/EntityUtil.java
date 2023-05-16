package dev.lotnest.adventure.common.util;

import fr.skytasul.glowingentities.GlowingEntities;
import lombok.SneakyThrows;
import net.minecraft.core.BlockPos;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundAddPlayerPacket;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoRemovePacket;
import net.minecraft.network.protocol.game.ClientboundPlayerInfoUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundRotateHeadPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.Entity;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;

import static java.util.Objects.requireNonNull;

public class EntityUtil {

    private EntityUtil() {
    }

    public static void updateClientboundEntityData(@NotNull ServerPlayer receivingPlayer, @NotNull Entity targetEntity) {
        ClientboundSetEntityDataPacket setEntityDataPacket = new ClientboundSetEntityDataPacket(targetEntity.getId(),
                targetEntity.getEntityData().getNonDefaultValues());
        receivingPlayer.connection.send(setEntityDataPacket);
    }

    public static void spawnClientboundEntity(@NotNull ServerPlayer receivingPlayer, @NotNull Entity targetEntity) {
        ClientboundAddEntityPacket addEntityPacket = new ClientboundAddEntityPacket(targetEntity);
        receivingPlayer.connection.send(addEntityPacket);

        updateClientboundEntityData(receivingPlayer, targetEntity);
    }

    public static void removeClientboundEntity(@NotNull ServerPlayer receivingPlayer, @NotNull Entity targetEntity) {
        ClientboundRemoveEntitiesPacket removeEntitiesPacket = new ClientboundRemoveEntitiesPacket(targetEntity.getId());
        receivingPlayer.connection.send(removeEntitiesPacket);
    }

    public static void spawnClientboundServerPlayer(@NotNull ServerPlayer receivingPlayer,
                                                    @NotNull ServerPlayer targetPlayer) {
        ServerGamePacketListenerImpl connection = receivingPlayer.connection;
        ClientboundPlayerInfoUpdatePacket playerInfoUpdatePacket = new ClientboundPlayerInfoUpdatePacket(
                ClientboundPlayerInfoUpdatePacket.Action.ADD_PLAYER, targetPlayer);
        ClientboundAddPlayerPacket addPlayerPacket = new ClientboundAddPlayerPacket(targetPlayer);

        connection.send(playerInfoUpdatePacket);
        connection.send(addPlayerPacket);

        updateClientboundServerPlayerRotation(receivingPlayer, targetPlayer, targetPlayer.getXRot(), targetPlayer.getYRot());
    }

    public static void updateClientboundServerPlayerRotation(@NotNull ServerPlayer receivingPlayer,
                                                             @NotNull ServerPlayer targetPlayer, float xRot, float yRot) {
        ServerGamePacketListenerImpl connection = receivingPlayer.connection;
        byte yaw = (byte) ((yRot * 256.0F) / 360.0F);
        ClientboundRotateHeadPacket rotateHeadPacket = new ClientboundRotateHeadPacket(targetPlayer, yaw);
        ClientboundMoveEntityPacket.Rot moveRotEntityPacket = new ClientboundMoveEntityPacket.Rot(targetPlayer.getId(), yaw,
                (byte) ((xRot * 256.0F) / 360.0F), targetPlayer.isOnGround());

        connection.send(rotateHeadPacket);
        connection.send(moveRotEntityPacket);
    }

    public static void removeClientboundServerPlayer(@NotNull ServerPlayer serverPlayer, @NotNull ServerPlayer targetPlayer) {
        ServerGamePacketListenerImpl connection = serverPlayer.connection;
        ClientboundPlayerInfoRemovePacket playerInfoRemovePacket = new ClientboundPlayerInfoRemovePacket(
                Collections.singletonList(targetPlayer.getUUID()));
        ClientboundRemoveEntitiesPacket removeEntitiesPacket = new ClientboundRemoveEntitiesPacket(targetPlayer.getId());

        connection.send(playerInfoRemovePacket);
        connection.send(removeEntitiesPacket);
    }

    @SneakyThrows
    public static void addClientboundGlowingToEntity(@NotNull GlowingEntities glowingApi, @NotNull Entity targetEntity,
                                                     @NotNull Player receivingPlayer, @NotNull ChatColor color) {
        addClientboundGlowingToEntity(glowingApi, targetEntity.getBukkitEntity(), receivingPlayer, color);
    }

    @SneakyThrows
    public static void addClientboundGlowingToEntity(@NotNull GlowingEntities glowingApi,
                                                     @NotNull org.bukkit.entity.Entity targetEntity,
                                                     @NotNull Player receivingPlayer, @NotNull ChatColor color) {
        glowingApi.setGlowing(targetEntity, receivingPlayer, color);
    }

    public static void makeClientboundServerPlayerSleep(@Nullable Player targetPlayer, @NotNull Block block) {
        if (targetPlayer == null) {
            return;
        }

        ServerPlayer serverPlayer = ((CraftPlayer) targetPlayer).getHandle();
        serverPlayer.startSleeping(new BlockPos(block.getX(), block.getY(), block.getZ()));
    }

    @NotNull
    public static ServerGamePacketListenerImpl newClientboundServerGamePacketListener(@NotNull Player player) {
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        return new ServerGamePacketListenerImpl(requireNonNull(serverPlayer.getServer()),
                new Connection(PacketFlow.CLIENTBOUND), serverPlayer);
    }

    public static void teleportClientboundEntity(@NotNull ServerPlayer receivingPlayer,
                                                 @NotNull org.bukkit.entity.Entity targetEntity, @NotNull Location location) {
        Entity entity = ((CraftPlayer) targetEntity).getHandle();
        entity.teleportTo(location.getX(), location.getY(), location.getZ());

        ClientboundTeleportEntityPacket teleportEntityPacket = new ClientboundTeleportEntityPacket(entity);
        receivingPlayer.connection.send(teleportEntityPacket);

        updateClientboundEntityData(receivingPlayer, entity);
    }

}
