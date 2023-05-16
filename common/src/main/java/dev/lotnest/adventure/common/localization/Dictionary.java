package dev.lotnest.adventure.common.localization;

import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Properties;

@RequiredArgsConstructor
public class Dictionary {

    private static final String DEFAULT_VALUE_FOR_KEY = "MISSING_VALUE_FOR_KEY_{0}";

    @NotNull
    private final Map<String, String> propertiesMap;

    public Dictionary(@NotNull String languagePropertiesFile) {
        this.propertiesMap = loadProperties(languagePropertiesFile);
    }

    @SneakyThrows
    @NotNull
    private Map<String, String> loadProperties(@NotNull String languagePropertiesFile) {
        Map<String, String> result = Maps.newHashMap();

        try (InputStream inputStream = Dictionary.class.getResourceAsStream(languagePropertiesFile)) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.forEach((key, value) -> result.put((String) key, (String) value));
        }

        return result;
    }

    @NotNull
    public String get(@NotNull String key) {
        return propertiesMap.getOrDefault(key, MessageFormat.format(DEFAULT_VALUE_FOR_KEY, key));
    }

    @NotNull
    public String get(@NotNull String key, @NotNull Object... placeholders) {
        return MessageFormat.format(get(key), placeholders);
    }

    @NotNull
    public String getOrElse(@NotNull String key, @NotNull String defaultValue) {
        return propertiesMap.getOrDefault(key, defaultValue);
    }

    @NotNull
    public String getOrElse(@NotNull String key, @NotNull String defaultValue, @NotNull Object... placeholders) {
        return MessageFormat.format(getOrElse(key, defaultValue), placeholders);
    }

}
