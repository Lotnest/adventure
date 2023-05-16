package dev.lotnest.adventure.common.item.builder;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.UUID;

public class SkullItemBuilder extends ItemBuilder {

    private OfflinePlayer owningPlayer;
    private String base64Texture;

    private SkullItemBuilder() {
    }

    @Contract(value = " -> new", pure = true)
    @NotNull
    public static SkullItemBuilder builder() {
        return new SkullItemBuilder();
    }

    @NotNull
    public SkullItemBuilder owningPlayer(@Nullable OfflinePlayer owningPlayer) {
        this.owningPlayer = owningPlayer;
        return this;
    }

    @NotNull
    public SkullItemBuilder skullTexture(@Nullable String base64Texture) {
        this.base64Texture = base64Texture;
        return this;
    }

    @Override
    @NotNull
    public ItemStack build() {
        ItemStack result = build(Material.PLAYER_HEAD, amount, displayName, lore, isUnbreakable, enchantments, itemFlags);

        if (owningPlayer != null) {
            SkullMeta skullMeta = (SkullMeta) result.getItemMeta();
            if (skullMeta != null) {
                skullMeta.setOwningPlayer(owningPlayer);
                result.setItemMeta(skullMeta);
            }
        }

        if (!StringUtils.isBlank(base64Texture)) {
            SkullMeta skullMeta = (SkullMeta) result.getItemMeta();
            if (skullMeta != null) {
                GameProfile profile = new GameProfile(UUID.randomUUID(), null);
                PropertyMap propertyMap = profile.getProperties();

                if (propertyMap == null) {
                    throw new IllegalStateException("Profile does not contain a property map");
                }

                propertyMap.put("textures", new Property("textures", base64Texture));

                Field profileField;
                Method setProfileMethod = null;

                try {
                    profileField = skullMeta.getClass().getDeclaredField("profile");
                    profileField.setAccessible(true);
                    profileField.set(skullMeta, profile);
                } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException exception) {
                    try {
                        setProfileMethod = skullMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                    } catch (NoSuchMethodException ignored) {
                    }
                } catch (SecurityException ignored) {
                }

                try {
                    if (setProfileMethod == null) {
                        profileField = skullMeta.getClass().getDeclaredField("profile");
                        profileField.setAccessible(true);
                        profileField.set(skullMeta, profile);
                    } else {
                        setProfileMethod.setAccessible(true);
                        setProfileMethod.invoke(skullMeta, profile);
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                result.setItemMeta(skullMeta);
            }
        }

        return result;
    }
}
