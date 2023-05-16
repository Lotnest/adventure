package dev.lotnest.adventure.common.character.ability;

import dev.lotnest.adventure.common.json.JsonParser;

public class AbilityParser extends JsonParser<Ability> {

    public AbilityParser() {
        super(Ability.class);
    }

}
