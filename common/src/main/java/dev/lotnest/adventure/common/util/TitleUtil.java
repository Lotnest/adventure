package dev.lotnest.adventure.common.util;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;

public class TitleUtil {

    public static final long DEFAULT_FADE_IN_TITLE_TICKS = TicksConverter.fromSeconds(1) - 10L;
    public static final long DEFAULT_STAY_TITLE_TICKS = TicksConverter.fromSeconds(3) + 10L;
    public static final long DEFAULT_FADE_OUT_TITLE_TICKS = TicksConverter.fromSeconds(1);
    public static final long DEFAULT_FULL_CYCLE_TICKS = DEFAULT_FADE_IN_TITLE_TICKS + DEFAULT_STAY_TITLE_TICKS +
            DEFAULT_FADE_OUT_TITLE_TICKS;
    public static final long DEFAULT_FADE_IN_TITLE_MILLIS = DEFAULT_FADE_IN_TITLE_TICKS * 50L;
    public static final long DEFAULT_STAY_TITLE_MILLIS = DEFAULT_STAY_TITLE_TICKS * 50L;
    public static final long DEFAULT_FADE_OUT_TITLE_MILLIS = DEFAULT_FADE_OUT_TITLE_TICKS * 50L;
    public static final long DEFAULT_FULL_CYCLE_MILLIS = DEFAULT_FADE_IN_TITLE_MILLIS + DEFAULT_STAY_TITLE_MILLIS +
            DEFAULT_FADE_OUT_TITLE_MILLIS;
    public static final Duration DEFAULT_FADE_IN_TITLE_MILLIS_DURATION = Duration.ofMillis(DEFAULT_FADE_IN_TITLE_MILLIS);
    public static final Duration DEFAULT_STAY_TITLE_MILLIS_DURATION = Duration.ofMillis(DEFAULT_STAY_TITLE_MILLIS);
    public static final Duration DEFAULT_FADE_OUT_TITLE_MILLIS_DURATION = Duration.ofMillis(DEFAULT_FADE_OUT_TITLE_MILLIS);
    public static final Duration DEFAULT_FULL_CYCLE_MILLIS_DURATION = Duration.ofMillis(DEFAULT_FULL_CYCLE_MILLIS);

    private TitleUtil() {
    }

    public static void sendTitle(@NotNull Audience audience, @NotNull Component titleComponent) {
        sendTitle(audience, titleComponent, Component.empty(), DEFAULT_FADE_IN_TITLE_MILLIS_DURATION,
                DEFAULT_STAY_TITLE_MILLIS_DURATION, DEFAULT_FADE_OUT_TITLE_MILLIS_DURATION);
    }

    public static void sendTitle(@NotNull Audience audience, @NotNull Component titleComponent,
                                 @NotNull Component subtitleComponent) {
        sendTitle(audience, titleComponent, subtitleComponent, DEFAULT_FADE_IN_TITLE_MILLIS_DURATION,
                DEFAULT_STAY_TITLE_MILLIS_DURATION, DEFAULT_FADE_OUT_TITLE_MILLIS_DURATION);
    }

    public static void sendTitle(@NotNull Audience audience, @NotNull Component titleComponent,
                                 @NotNull Component subtitleComponent, @NotNull Duration fadeInDuration,
                                 @NotNull Duration stayDuration, @NotNull Duration fadeOutDuration) {
        Title.Times titleTimes = Title.Times.times(fadeInDuration, stayDuration, fadeOutDuration);
        Title title = Title.title(titleComponent, subtitleComponent, titleTimes);
        audience.showTitle(title);
    }
}
