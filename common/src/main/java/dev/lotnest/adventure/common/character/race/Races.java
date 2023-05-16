package dev.lotnest.adventure.common.character.race;

import static java.util.Objects.requireNonNull;

public class Races {

    private static final RaceParser RACE_PARSER = new RaceParser();

    public static final Race HUMAN = RACE_PARSER.parseFile(
            requireNonNull(Races.class.getResourceAsStream("/races/human.json")));

    private Races() {
    }

}
