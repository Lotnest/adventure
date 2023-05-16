package dev.lotnest.adventure.common.character.ability;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.lotnest.adventure.common.message.MessageSender;
import dev.lotnest.adventure.common.player.AdventurePlayer;
import dev.lotnest.adventure.common.player.Mana;
import dev.lotnest.adventure.common.scheduler.SchedulerUtil;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.index.qual.Positive;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

public abstract class AbilityHandler {

    private static final String ABILITY_CLICK_SEQUENCE_TITLE_FORMAT = ChatColor.GREEN + "R" + ChatColor.WHITE + "-"
            + ChatColor.GREEN + "?" + ChatColor.WHITE + "-" + ChatColor.GREEN + "?";

    @Getter
    @NotNull
    private final Ability ability;

    @NotNull
    private final MessageSender messageSender;

    @NotNull
    private final Cache<AdventurePlayer, Long> cooldowns;

    @NotNull
    private final Logger logger;

    @NotNull
    private final ConcurrentMap<Player, BossBar> abilityProgressBossBars = Maps.newConcurrentMap();

    @NotNull
    private final Cache<Player, List<Character>> abilityActivationClickSequence = CacheBuilder.newBuilder()
            .expireAfterWrite(3, TimeUnit.SECONDS)
            .build();

    protected AbilityHandler(@NotNull Ability ability, @NotNull MessageSender messageSender, @NotNull Logger logger) {
        this.ability = ability;
        this.messageSender = messageSender;
        this.logger = logger;
        this.cooldowns = CacheBuilder.newBuilder()
                .expireAfterWrite(ability.getCooldownInSeconds(), TimeUnit.SECONDS)
                .build();
    }

    /**
     * Called when the ability is marked for execution, at this point the cooldown is <b>already calculated</b> and the
     * ability is now going to be executed.
     */
    public abstract void execute(@NotNull Plugin plugin, @NotNull AdventurePlayer adventurePlayer);

    @NotNull
    public abstract Map<Attribute, AttributeModifier> getAttributeModifiers();

    /**
     * @return {@link Optional#of(Object)}} duration of the ability in seconds as {@link Integer}, if the ability has no duration,
     * return {@link Optional#empty()}
     */
    @NotNull
    public final Optional<Integer> getDuration() {
        return Optional.ofNullable(ability.getDurationInSeconds());
    }

    public final long getDurationInTicks() {
        return getDuration().orElse(0) * 20L;
    }

    /**
     * @return cooldown of the ability in seconds
     */
    @Positive
    public final int getCooldown() {
        return ability.getCooldownInSeconds();
    }

    @Positive
    public final long getCooldownInTicks() {
        return getCooldown() * 20L;
    }

    public final int getCooldownTimeLeft(@NotNull AdventurePlayer adventurePlayer) {
        Long cooldownStartInMillis = cooldowns.getIfPresent(adventurePlayer);
        if (cooldownStartInMillis == null) {
            return 0;
        }

        long elapsedTimeInMillis = System.currentTimeMillis() - cooldownStartInMillis;
        long remainingTimeInMillis = getCooldown() * 1000L - elapsedTimeInMillis;
        return (int) TimeUnit.MILLISECONDS.toSeconds(remainingTimeInMillis);
    }

    /**
     * @return true if the ability can be executed (adventurePlayer is not in cooldown cache), false otherwise
     * @see #getCooldownTimeLeft(AdventurePlayer)
     */
    public boolean canExecute(@NotNull AdventurePlayer adventurePlayer) {
        return getCooldownTimeLeft(adventurePlayer) == 0;
    }

    public void startAbility(@NotNull Plugin plugin, @NotNull AdventurePlayer adventurePlayer) {
        cooldowns.put(adventurePlayer, System.currentTimeMillis());
        applyAttributeModifiers(adventurePlayer.getPlayer().orElse(null));
        execute(plugin, adventurePlayer);
    }

    /**
     * Called when the ability is requested to be executed, at this point the cooldown will be calculated and the ability
     * will be marked for execution only if the cooldown check passes. Do not override this method, unless completely
     * necessary.
     *
     * @see #canExecute(AdventurePlayer)
     */
    public void onAbilityStart(@NotNull Plugin plugin, @NotNull AdventurePlayer adventurePlayer) {
        if (!canExecute(adventurePlayer)) {
            logger.debug("{} cannot be executed for {} because the cooldown is not over", getClass().getSimpleName(),
                    adventurePlayer.getOfflinePlayer().getName());
            return;
        }

        logger.debug("{} started for {}", getClass().getSimpleName(), adventurePlayer.getOfflinePlayer().getName());

        startAbility(plugin, adventurePlayer);

        Player player = adventurePlayer.getPlayer().orElse(null);
        messageSender.sendMessage(player, ability.getName() + ChatColor.GREEN + " ability activated!");

        SchedulerUtil.scheduleSyncRepeatingTaskWithCancel(plugin, () -> updateCooldownProgressBar(adventurePlayer),
                0L, 20L, getCooldownInTicks(), () -> {
                    if (player != null) {
                        BossBar bossBar = abilityProgressBossBars.get(player);
                        if (bossBar != null) {
                            bossBar.removePlayer(player);
                            abilityProgressBossBars.remove(player);
                        }
                    }
                });

        Bukkit.getScheduler().runTaskLater(plugin, () -> onAbilityEnd(adventurePlayer), getDurationInTicks());
    }

    public void endAbility(@Nullable Player player) {
        removeAttributeModifiers(player);
    }

    public void onAbilityEnd(@NotNull AdventurePlayer adventurePlayer) {
        logger.debug("{} ended for {}", getClass().getSimpleName(), adventurePlayer.getOfflinePlayer().getName());
        Player player = adventurePlayer.getPlayer().orElse(null);
        endAbility(player);
        messageSender.sendMessage(player, ability.getName() + ChatColor.RED + " ability has worn off!");
    }

    public void updateCooldownProgressBar(@NotNull AdventurePlayer adventurePlayer) {
        adventurePlayer.getPlayer().ifPresent(player -> {
            int cooldownTimeLeft = getCooldownTimeLeft(adventurePlayer);
            if (cooldownTimeLeft > 0) {
                BossBar bossBar = abilityProgressBossBars.computeIfAbsent(player, playerIfAbsent ->
                        Bukkit.createBossBar(ChatColor.YELLOW + "Ability cooldown", BarColor.RED, BarStyle.SEGMENTED_20));
                bossBar.setProgress((double) cooldownTimeLeft / getCooldown());
                bossBar.addPlayer(player);
            }
        });
    }

    public void applyAttributeModifiers(@Nullable Player player) {
        if (player == null) {
            logger.warn("Cannot apply attribute modifiers for null player");
            return;
        }

        logger.debug("Applying attribute modifiers for player {}", player.getName());

        getAttributeModifiers().forEach((attribute, attributeModifier) -> {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                Collection<AttributeModifier> attributeModifiers = attributeInstance.getModifiers();
                if (!attributeModifiers.contains(attributeModifier)) {
                    attributeInstance.addModifier(attributeModifier);

                    logger.debug("Added attribute modifier {} to player {}", attributeModifier, player.getName());
                    logger.debug("Attribute {} now has {} modifiers for player {}", attribute,
                            Objects.requireNonNull(player.getAttribute(attribute)).getModifiers(), player.getName());
                }
            }
        });
    }

    public void removeAttributeModifiers(@Nullable Player player) {
        if (player == null) {
            logger.warn("Cannot remove attribute modifiers for null player");
            return;
        }

        logger.debug("Removing attribute modifiers for player {}", player.getName());

        getAttributeModifiers().forEach((attribute, attributeModifier) -> {
            AttributeInstance attributeInstance = player.getAttribute(attribute);
            if (attributeInstance != null) {
                attributeInstance.getModifiers().forEach(attributeInstanceAttributeModifier -> {
                    if (attributeInstanceAttributeModifier.getName().equals(attributeModifier.getName())) {
                        attributeInstance.removeModifier(attributeInstanceAttributeModifier);

                        logger.debug("Removed attribute modifier {} from player {}", attributeInstanceAttributeModifier, player.getName());
                        logger.debug("Attribute {} now has {} modifiers for player {}", attribute, attributeInstance.getModifiers(),
                                player.getName());
                    }
                });
            }
        });
    }

    public void handleAbilityClickSequenceClick(@NotNull Plugin plugin, @NotNull AdventurePlayer adventurePlayer, @NotNull Action action) {
        if (action == Action.PHYSICAL) {
            return;
        }

        Player player = adventurePlayer.getPlayer().orElse(null);
        if (player == null) {
            return;
        }

        Mana mana = adventurePlayer.getMana();
        if (mana == null) {
            return;
        }

        List<Character> abilityActivationSequence = abilityActivationClickSequence.getIfPresent(player);
        if (abilityActivationSequence == null) {
            if (isRightClick(action)) {
                abilityActivationSequence = Lists.newArrayList('R');
                abilityActivationClickSequence.put(player, abilityActivationSequence);
                player.sendTitle(ABILITY_CLICK_SEQUENCE_TITLE_FORMAT, "", 2, 25, 2);
            }
        } else if (abilityActivationSequence.size() < 3) {
            char firstActionLetter = action.name().charAt(0);
            abilityActivationSequence.add(firstActionLetter);

            if (abilityActivationSequence.size() != 3) {
                String title = getAbilityClickSequenceTitle(abilityActivationSequence);
                player.sendTitle(title, "", 2, 25, 2);
            } else {
                logger.debug("Cooldown: {}", cooldowns.getIfPresent(adventurePlayer));
                if (isValidAbilityActivationSequence(abilityActivationSequence, adventurePlayer, player, mana)) {
                    player.sendTitle(
                            getAbilityClickSequenceTitle(abilityActivationSequence)
                                    .replace("?", "R"), ChatColor.GREEN + "Ability activated!", 2, 25, 2
                    );
                    mana.removeMana(ability.getManaCost());
                    onAbilityStart(plugin, adventurePlayer);
                }

                abilityActivationClickSequence.invalidate(player);
            }
        }
    }

    private boolean isRightClick(Action action) {
        return action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK;
    }

    @NotNull
    private String getAbilityClickSequenceTitle(@NotNull List<Character> abilityActivationSequence) {
        return ABILITY_CLICK_SEQUENCE_TITLE_FORMAT.replaceFirst("\\?", abilityActivationSequence.get(1).toString());
    }

    @NotNull
    private String getInvalidSequenceTitle(@NotNull List<Character> abilityActivationSequence) {
        return ABILITY_CLICK_SEQUENCE_TITLE_FORMAT.replaceFirst("\\?", abilityActivationSequence.get(1).toString())
                .replaceFirst("\\?", abilityActivationSequence.get(2).toString());
    }

    private boolean isValidAbilityActivationSequence(@NotNull List<Character> abilityActivationSequence,
                                                     @NotNull AdventurePlayer adventurePlayer, @NotNull Player player,
                                                     @NotNull Mana mana) {
        logger.debug("Cooldown2: {}", cooldowns.getIfPresent(adventurePlayer));

        if (abilityActivationSequence.get(0) != 'R' || abilityActivationSequence.get(1) != 'R'
                || abilityActivationSequence.get(2) != 'R') {
            player.sendTitle(
                    getInvalidSequenceTitle(abilityActivationSequence), ChatColor.RED + "Invalid sequence!", 2, 25, 2
            );
            return false;
        }

        String abilityClickSequenceMessageFormatted = ABILITY_CLICK_SEQUENCE_TITLE_FORMAT.replace("?", "R");

        if (!canExecute(adventurePlayer)) {
            player.sendTitle(abilityClickSequenceMessageFormatted, ChatColor.RED + "Ability is still on cooldown!",
                    2, 25, 2);
            return false;
        }

        if (mana.getCurrentMana() < ability.getManaCost()) {
            player.sendTitle(abilityClickSequenceMessageFormatted, ChatColor.RED + "Not enough mana!", 2, 25, 2);
            return false;
        }

        return true;
    }

}
