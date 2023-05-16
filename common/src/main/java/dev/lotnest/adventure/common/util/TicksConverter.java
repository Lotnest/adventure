package dev.lotnest.adventure.common.util;

public class TicksConverter {

    public static final long ONE_SECOND = fromSeconds(1);
    public static final long TWO_SECONDS = fromSeconds(2);
    public static final long THREE_SECONDS = fromSeconds(3);
    public static final long FOUR_SECONDS = fromSeconds(4);
    public static final long FIVE_SECONDS = fromSeconds(5);
    public static final long SIX_SECONDS = fromSeconds(6);
    public static final long SEVEN_SECONDS = fromSeconds(7);
    public static final long EIGHT_SECONDS = fromSeconds(8);
    public static final long NINE_SECONDS = fromSeconds(9);
    public static final long TEN_SECONDS = fromSeconds(10);

    private TicksConverter() {
    }

    public static long fromMillis(long millis) {
        return millis / 50L;
    }

    public static long fromNanos(long nanos) {
        return fromMillis(nanos / 1000000L);
    }

    public static long fromSeconds(int seconds) {
        return seconds * 20L;
    }

    public static long fromMinutes(int minutes) {
        return fromSeconds(minutes * 60);
    }

    public static long fromHours(int hours) {
        return fromMinutes(hours * 60);
    }

    public static long fromDays(int days) {
        return fromHours(days * 24);
    }

    public static long fromWeeks(int weeks) {
        return fromDays(weeks * 7);
    }

    public static long fromMonths(int months) {
        return fromDays(months * 30);
    }

}
