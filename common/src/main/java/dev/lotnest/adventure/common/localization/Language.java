package dev.lotnest.adventure.common.localization;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public enum Language {

    EN_US("en_us", "English (United States)",
            Lists.newArrayList(UUID.fromString("06bfd23d-6fe5-4e6f-a411-8eaab63a9ef1")));

    private final String code;
    private final String name;
    private final List<UUID> authorUuids;

}
