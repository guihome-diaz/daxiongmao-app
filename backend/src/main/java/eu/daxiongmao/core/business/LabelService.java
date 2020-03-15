package eu.daxiongmao.core.business;

import eu.daxiongmao.core.business.cache.InnerCache;
import eu.daxiongmao.core.dao.LabelRepository;
import eu.daxiongmao.core.model.db.Label;
import eu.daxiongmao.core.model.dto.LabelDTO;
import eu.daxiongmao.core.model.enums.AppLang;
import eu.daxiongmao.core.model.enums.param.BusinessParam;
import eu.daxiongmao.core.model.enums.param.TechnicalParam;
import eu.daxiongmao.core.model.mapper.LabelMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * To interact with application's labels.
 * @author Guillaume Diaz
 * @version 1.0 - 2020/03
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class LabelService {

    /** Hard-coded fallback value in case of DB failure or bad configuration */
    private final AppLang FAILOVER_DEFAULT_APP_LANGUAGE = AppLang.ENGLISH;

    private final ParameterService parameterService;
    private final LabelRepository labelRepository;
    private final LabelMapper labelMapper;
    private InnerCache<String, Label> cache;

    @PostConstruct
    public void setup() {
        // max refresh delay
        final Optional<Object> delayBetweenRefreshInSeconds = parameterService.getValue(TechnicalParam.MIN_TIME_IN_SECONDS_BETWEEN_CACHE_REFRESH);
        // Function to load cache
        cache = new InnerCache<>(log, (Integer) delayBetweenRefreshInSeconds.orElseThrow(), () -> {
            // Get DB values
            final List<Label> dbValues = labelRepository.findAll();
            // Update local cache
            final Map<String, Label> valuesToCache = new HashMap<>(dbValues.size());
            if (!dbValues.isEmpty()) {
                dbValues.forEach((dbLabel) -> {
                    // Each label has an unique code, make it toUpperCase() for case insensitive search
                    valuesToCache.put(dbLabel.getCode().toUpperCase(), dbLabel);
                });
            }
            log.info("Initialization complete | {} Labels have been cached in memory", dbValues.size());
            return valuesToCache;
        });
        // Populate cache on startup
        cache.updateCache(true);
    }


    /**
     * To retrieve a label by its code.
     * This will use the local cache
     * @param labelCode search label code
     * @return corresponding DTO or null
     */
    public Optional<LabelDTO> getByCode(final String labelCode) {
        if (StringUtils.isBlank(labelCode)) {
            return Optional.empty();
        }
        // Retrieve value from cache
        final Label label = cache.getCachedValues().get(labelCode.toUpperCase());
        if (label == null) {
            return Optional.empty();
        }
        // Validity check (must be enabled)
        if (!label.getIsActive()) {
            log.warn("Data leak avoidance|Someone tried to view a disabled label {}", labelCode);
            return Optional.empty();
        }
        // Conversion to DTO
        return Optional.ofNullable(labelMapper.dbEntityToDto(label));
    }

    /**
     * To retrieve a label content by its code and language
     * This will use the local cache
     * @param labelCode search label code
     * @param searchLang requested language
     * @return corresponding DTO or null
     */
    public Optional<String> getByCodeAndLanguage(final String labelCode, final AppLang searchLang) {
        // Get DB content
        Optional<LabelDTO> label = getByCode(labelCode);
        if (label.isEmpty()) {
            return Optional.empty();
        }

        // Language check
        final String textSearchLang = label.get().getLanguageTranslation(searchLang);
        if (textSearchLang != null) {
            // search lang is available
            return Optional.of(textSearchLang);
        } else {
            // search lang is not available => use default translation or NULL
            final AppLang defaultLang = getDefaultLanguage();
            return Optional.ofNullable(label.get().getLanguageTranslation(defaultLang));
        }
    }


    /**
     * To retrieve the default application's language
     * @return default application's language
     */
    public AppLang getDefaultLanguage() {
        final AppLang defaultLanguage = FAILOVER_DEFAULT_APP_LANGUAGE;

        // Get DB setting
        final Optional<Object> defaultLangParam = parameterService.getValue(BusinessParam.APP_DEFAULT_LANGUAGE);
        if (defaultLangParam.isEmpty()) {
            // No DB settings have been found => use hard-coded value as default
            return defaultLanguage;
        } else {
            // DB value has been retrieved
            final String langCode = (String) defaultLangParam.get();
            Optional<AppLang> langCodeEnum = AppLang.getLanguageForCode(langCode);
            // return DB code || or use hard-coded value as default
            return langCodeEnum.orElse(defaultLanguage);
        }
    }

    /**
     * To retrieve the application's language that matches the given LOCALE or default language
     * @param locale search locale
     * @return corresponding language to use (or default application's language)
     */
    public AppLang getLanguageForLocale(final Locale locale) {
        // arg check
        if (locale == null) {
            return getDefaultLanguage();
        }

        // Process given locale
        Optional<AppLang> langCodeEnum = AppLang.getLanguageForCode(locale.getLanguage());
        // Return locale if supported || or default language as fallback
        return langCodeEnum.orElse(getDefaultLanguage());
    }

}
