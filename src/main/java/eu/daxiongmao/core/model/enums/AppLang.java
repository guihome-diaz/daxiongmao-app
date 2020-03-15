package eu.daxiongmao.core.model.enums;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Set of available languages
 * @version 1.0
 * @since beginning of times
 * @author Guillaume Diaz
 */
@Log4j2
public enum AppLang {

    /** Chinese (mandarin) language */
    CHINESE("ZH"),

    /** English (UK) language */
    ENGLISH("EN"),

    /** French (France) language */
    FRENCH("FR");

    /** Language code in 2 letters (ex: FR, EN, DE, ZH, ..) */
    private String languageCode;

    /**
     * @param languageCode Language code in 2 letters (ex: FR, EN, DE, ZH, ..)
     */
    AppLang(String languageCode) {
        this.languageCode = languageCode;
    }

    /**
     * @return Language code in 2 letters (ex: FR, EN, DE, ZH, ..)
     */
    public String getLanguageCode() {
        return languageCode;
    }

    /**
     * To return the language enum corresponding to given lang code (2 letters), or NULL if nothing matches
     * @param searchLang search language code (in 2 letters)
     * @return corresponding language or NULL if the application does not have the requested language configured
     */
    public static Optional<AppLang> getLanguageForCode(String searchLang) {
        if (StringUtils.isBlank(searchLang)) {
            return Optional.empty();
        }
        final String trimSearchLang = searchLang.trim();
        if (searchLang.trim().length() > 2) {
            log.warn("Invalid language code received: " + trimSearchLang);
            return Optional.empty();
        }
        AppLang targetLang = null;
        for (AppLang langCode : values()) {
            if (langCode.getLanguageCode().equalsIgnoreCase(trimSearchLang)) {
                targetLang = langCode;
                break;
            }
        }
        return Optional.ofNullable(targetLang);
    }
}
