package dev.lotnest.adventure.common.potion;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffects {

    public static final PotionEffect PERMANENT_SLOWNESS_2 = new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 1);

    public static final PotionEffect PERMANENT_SPEED_2 = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1);

    public static final PotionEffect PERMANENT_NIGHT_VISION = new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0);

    public static final PotionEffect PERMANENT_INVISIBILITY = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 0);

    public static final PotionEffect PERMANENT_BLINDNESS = new PotionEffect(PotionEffectType.BLINDNESS, Integer.MAX_VALUE, 0);

    private PotionEffects() {
    }

}
