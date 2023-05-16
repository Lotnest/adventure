package dev.lotnest.adventure.common.chapter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.Month;

@RequiredArgsConstructor
@Getter
public enum ChapterType {

    ZERO(0, "Chapter 0", LocalDate.of(2023, Month.APRIL, 5), null);

    private final int number;

    @NotNull
    private final String name;

    @NotNull
    private final LocalDate developmentStartDate;

    @Nullable
    private final LocalDate releaseDate;

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("number", number)
                .append("name", name)
                .append("developmentStartDate", developmentStartDate)
                .append("releaseDate", releaseDate)
                .toString();
    }

}
