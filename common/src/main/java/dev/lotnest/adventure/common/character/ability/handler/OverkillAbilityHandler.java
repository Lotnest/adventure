package dev.lotnest.adventure.common.character.ability.handler;

import com.google.common.collect.Maps;
import dev.lotnest.adventure.common.character.ability.Abilities;
import dev.lotnest.adventure.common.character.ability.AbilityHandler;
import dev.lotnest.adventure.common.logging.Loggers;
import dev.lotnest.adventure.common.message.MessageSender;
import dev.lotnest.adventure.common.message.MessageSenders;
import dev.lotnest.adventure.common.player.AdventurePlayer;
import dev.lotnest.adventure.common.scheduler.SchedulerUtil;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Map;
import java.util.UUID;

public class OverkillAbilityHandler extends AbilityHandler {

    private static final float SPEED_BOOST_MULTIPLIER = 1.35F;
    private static final float STRENGTH_BOOST_MULTIPLIER = SPEED_BOOST_MULTIPLIER;

    private static final String SPEED_BOOST_ATTRIBUTE_NAME = "Overkill Speed Boost";
    private static final String STRENGTH_BOOST_ATTRIBUTE_NAME = "Overkill Strength Boost";

    private static final AttributeModifier SPEED_BOOST_ATTRIBUTE_MODIFIER = new AttributeModifier(UUID.randomUUID(),
            SPEED_BOOST_ATTRIBUTE_NAME, SPEED_BOOST_MULTIPLIER, AttributeModifier.Operation.MULTIPLY_SCALAR_1);
    private static final AttributeModifier STRENGTH_BOOST_ATTRIBUTE_MODIFIER = new AttributeModifier(UUID.randomUUID(),
            STRENGTH_BOOST_ATTRIBUTE_NAME, STRENGTH_BOOST_MULTIPLIER, AttributeModifier.Operation.MULTIPLY_SCALAR_1);

    private static final int PARTICLE_COUNT = 20;
    private static final double PARTICLE_RADIUS = 1.0;
    private static final double PARTICLE_HEIGHT_PER_LOOP = 0.1;
    private static final Particle.DustOptions PARTICLE_DUST_OPTIONS = new Particle.DustOptions(Color.RED, 1);

    private final Map<Player, Double> currentParticleAngle = Maps.newHashMap();
    private final long serverStartTime = System.currentTimeMillis();

    public OverkillAbilityHandler() {
        super(Abilities.OVERKILL, MessageSenders.COLORED, Loggers.DEFAULT);
    }

    public OverkillAbilityHandler(@NotNull MessageSender messageSender, @NotNull Logger logger) {
        super(Abilities.OVERKILL, messageSender, logger);
    }

    @Override
    public void execute(@NotNull Plugin plugin, @NotNull AdventurePlayer adventurePlayer) {
        SchedulerUtil.scheduleSyncRepeatingTaskWithCancel(plugin, () -> adventurePlayer.getPlayer()
                .ifPresent(this::spawnHelixParticles), 0L, 1L, getDurationInTicks());
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getAttributeModifiers() {
        return Map.of(Attribute.GENERIC_MOVEMENT_SPEED, SPEED_BOOST_ATTRIBUTE_MODIFIER,
                Attribute.GENERIC_ATTACK_DAMAGE, STRENGTH_BOOST_ATTRIBUTE_MODIFIER);
    }

    public void spawnHelixParticles(@NotNull Player player) {
        if (player.isOnline()) {
            Location playerLocation = player.getLocation();
            World world = player.getWorld();

            currentParticleAngle.putIfAbsent(player, 0.0);
            double currentAngle = currentParticleAngle.get(player);

            double yawRadians = Math.toRadians(player.getLocation().getYaw() + 90);
            double xRotation = Math.cos(yawRadians);
            double zRotation = Math.sin(yawRadians);

            for (int i = 0; i < PARTICLE_COUNT; i++) {
                double x = PARTICLE_RADIUS * Math.cos(currentAngle);
                double y = (i * PARTICLE_HEIGHT_PER_LOOP + (System.currentTimeMillis() - serverStartTime) * 0.001) % PARTICLE_RADIUS;
                double z = PARTICLE_RADIUS * Math.sin(currentAngle);

                double rotatedX = x * xRotation + z * zRotation;
                double rotatedZ = z * xRotation - x * zRotation;

                Vector offset = new Vector(rotatedX, y, rotatedZ);
                Location particleLoc = playerLocation.clone().add(offset);
                world.spawnParticle(Particle.REDSTONE, particleLoc, 1, PARTICLE_DUST_OPTIONS);
                currentAngle += 2 * Math.PI / PARTICLE_COUNT;
            }

            currentParticleAngle.put(player, currentAngle);
        }
    }

}
