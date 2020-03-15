package eu.daxiongmao.core.model.dto;


import eu.daxiongmao.core.model.enums.AppLang;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Application label
 * @version 1.0 - 2020/03
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "code", "english" })
@EqualsAndHashCode(of = {"code"})
public class LabelDTO implements Serializable {

    private static final long serialVersionUID = 20200312L;

    @NotBlank
    @Max(250)
    private String code;
    public void setCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            this.code = code.trim().toUpperCase();
        } else {
            this.code = null;
        }
    }

    @Max(2000)
    private String chinese;

    @Max(2000)
    private String english;

    @Max(2000)
    private String french;

    /**
     * To retrieve the list of available languages for the requested label code
     * @return available languages
     */
    public Set<AppLang> availableLanguages() {
        HashSet<AppLang> labelLanguages = new HashSet<>();
        if (chinese != null) { labelLanguages.add(AppLang.CHINESE); }
        if (english != null) { labelLanguages.add(AppLang.ENGLISH); }
        if (french != null) { labelLanguages.add(AppLang.FRENCH); }
        return labelLanguages;
    }

    /**
     * To retrieve the translation in a particular language
     * @param searchLang search language
     * @return corresponding text or NULL
     */
    public String getLanguageTranslation(AppLang searchLang) {
        String langText = null;
        switch (searchLang) {
            case CHINESE: langText = this.getChinese(); break;
            case ENGLISH: langText = this.getEnglish(); break;
            case FRENCH: langText = this.getFrench(); break;
            default:
                // do nothing
                break;
        }
        return langText;
    }
}
