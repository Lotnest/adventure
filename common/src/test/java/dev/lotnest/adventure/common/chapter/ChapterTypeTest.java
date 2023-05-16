package dev.lotnest.adventure.common.chapter;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class ChapterTypeTest {

    @Test
    void testChapterTypeZero() {
        // GIVEN
        ChapterType chapterTypeZero = ChapterType.ZERO;

        // THEN
        assertThat(chapterTypeZero.getNumber()).isZero();
        assertThat(chapterTypeZero.getName()).isEqualTo("Chapter 0");
        assertThat(chapterTypeZero.getDevelopmentStartDate()).isEqualTo(LocalDate.of(2023, Month.APRIL, 5));
        assertThat(chapterTypeZero.getReleaseDate()).isNull();
        assertThat(chapterTypeZero.toString()).hasToString("ChapterType[number=0,name=Chapter 0,developmentStartDate=2023-04-05,releaseDate=<null>]");
    }
}
