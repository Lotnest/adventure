package dev.lotnest.adventure.common.util;

import java.util.Random;

public class SoundUtil {

    private static Random RANDOM = new Random();

    private SoundUtil() {
    }

    public static float getRandomPitch() {
        return RANDOM.nextFloat() * (2F - 0.5F) + 0.5F;
    }
}
