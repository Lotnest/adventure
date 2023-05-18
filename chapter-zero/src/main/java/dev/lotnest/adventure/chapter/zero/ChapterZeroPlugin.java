package dev.lotnest.adventure.chapter.zero;

import dev.lotnest.adventure.chapter.zero.listener.ChapterZeroTest;
import dev.lotnest.adventure.common.character.race.Race;
import dev.lotnest.adventure.common.listener.ArrowHitRemover;
import dev.lotnest.adventure.common.listener.BloodEffectHandler;
import dev.lotnest.adventure.common.listener.HungerBlocker;
import dev.lotnest.adventure.common.listener.KitItemDropBlocker;
import dev.lotnest.adventure.common.listener.MobDamageListener;
import dev.lotnest.adventure.common.listener.MobOnDeathItemDropListener;
import dev.lotnest.adventure.common.listener.PlayerArrowShootListener;
import dev.lotnest.adventure.common.listener.PlayerSelfArrowDamageBlocker;
import dev.lotnest.adventure.common.listener.gui.CharacterMenuHandler;
import dev.lotnest.adventure.common.listener.gui.SettingsMenuHandler;
import dev.lotnest.adventure.common.player.AdventurePlayer;
import dev.lotnest.adventure.common.player.AdventurePlayerFactory;
import fr.skytasul.glowingentities.GlowingEntities;
import lombok.Getter;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Getter
public final class ChapterZeroPlugin extends JavaPlugin {

    private static ChapterZeroPlugin instance;

    private GlowingEntities glowingApi;
    private BukkitAudiences adventureApi;

    @Override
    public void onEnable() {
        glowingApi = new GlowingEntities(this);
        adventureApi = BukkitAudiences.create(this);

        Bukkit.getOnlinePlayers().forEach(AdventurePlayerFactory::createAdventurePlayer);

        registerListener(new ChapterZeroTest());
        registerListener(new HungerBlocker());
        registerListener(new ArrowHitRemover());
        registerListener(new PlayerSelfArrowDamageBlocker());
        registerListener(new KitItemDropBlocker());
        registerListener(new MobDamageListener());
        registerListener(new MobOnDeathItemDropListener());
        registerListener(new PlayerArrowShootListener(this));
        registerListener(new BloodEffectHandler());

        registerListener(CharacterMenuHandler.INSTANCE);
        registerListener(SettingsMenuHandler.INSTANCE);
    }

    @Override
    public void onDisable() {
        glowingApi.disable();
        adventureApi.close();

        Bukkit.getOnlinePlayers().forEach(player -> {
            AdventurePlayer adventurePlayer = AdventurePlayerFactory.createAdventurePlayer(player);
            Race playerRace = adventurePlayer.getRace();

            if (playerRace == null) {
                return;
            }

            playerRace.getAbilityHandler().endAbility(adventurePlayer.getPlayer().orElse(null));
        });
    }

    private void registerListener(@NotNull Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, this);
    }

    public static ChapterZeroPlugin getInstance() {
        if (instance == null) {
            instance = (ChapterZeroPlugin) Bukkit.getPluginManager().getPlugin("AdventureChapterZero");
        }
        return instance;
    }

}
