package dev.lotnest.adventure.common.character.ability;

import static java.util.Objects.requireNonNull;

public class Abilities {

    private static final AbilityParser ABILITY_PARSER = new AbilityParser();

    public static final Ability OVERKILL = ABILITY_PARSER.parseFile(
            requireNonNull(Abilities.class.getResourceAsStream("/abilities/overkill.json")));

    private Abilities() {
    }

}
