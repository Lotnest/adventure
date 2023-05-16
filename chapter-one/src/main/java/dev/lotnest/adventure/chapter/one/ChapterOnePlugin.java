package dev.lotnest.adventure.chapter.one;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChapterOnePlugin extends JavaPlugin {

    private static ChapterOnePlugin instance;

    @Override
    public void onEnable() {
    }

    public static ChapterOnePlugin getInstance() {
        if (instance == null) {
            instance = (ChapterOnePlugin) Bukkit.getPluginManager().getPlugin("AdventureChapterOne");
        }
        return instance;
    }
}
