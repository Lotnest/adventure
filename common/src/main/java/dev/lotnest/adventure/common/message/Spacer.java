package dev.lotnest.adventure.common.message;

import org.jetbrains.annotations.NotNull;

public class Spacer {

    private Spacer() {
    }

    @NotNull
    public static String tab(int amount) {
        return "    ".repeat(amount);
    }

    @NotNull
    public static String tab() {
        return tab(1);
    }

}
