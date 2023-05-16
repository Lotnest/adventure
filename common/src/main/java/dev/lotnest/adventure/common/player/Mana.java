package dev.lotnest.adventure.common.player;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mana {

    public static final int DEFAULT_STARTING_MANA = 40;

    private int currentMana;
    private int maxMana;

    public void removeMana(int mana) {
        currentMana -= mana;
    }

    public void addMana(int mana) {
        currentMana += mana;
    }

    public void fillMana() {
        currentMana = maxMana;
    }

}
