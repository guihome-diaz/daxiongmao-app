package eu.daxiongmao.core.model.db;

import eu.daxiongmao.core.model.enums.AppLang;
import lombok.*;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

/**
 * Label (text available in many languages)
 * @version 1.0 - 2020/03
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "code", "english","french","chinese" })
@EqualsAndHashCode(of = {"code"})
@Entity
@Table(name = "LABELS", indexes = {
        @Index(name = "LABELS_CODE_IDX", unique = true, columnList = "CODE"),
        @Index(name = "LABELS_ACTIVE_CODE_IDX", columnList = "CODE, IS_ACTIVE"),
        @Index(name = "LABELS_ACTIVE_IDX", columnList = "IS_ACTIVE")
})
public class Label extends GenericEntity {

    private static final long serialVersionUID = 20200312L;

    @Id
    @Column(name = "LABEL_ID")
    @SequenceGenerator(name = "seqLabels", sequenceName = "SEQ_LABELS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqLabels")
    private Long id;

    @NotBlank
    @Max(250)
    @Column(name = "CODE", nullable = false, length = 250)
    private String code;
    public void setCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            this.code = code.trim().toUpperCase();
        } else {
            this.code = null;
        }
    }

    @Max(2000)
    @Column(name = "LANG_ZH", length = 2000)
    private String chinese;
    public void setChinese(String text) {
        setLang(AppLang.CHINESE, text);
    }

    @Max(2000)
    @Column(name = "LANG_EN", length = 2000)
    private String english;
    public void setEnglish(String text) {
        setLang(AppLang.ENGLISH, text);
    }

    @Max(2000)
    @Column(name = "LANG_FR", length = 2000)
    private String french;
    public void setFrench(String text) {
       setLang(AppLang.FRENCH, text);
    }

    public Label() {
        super();
    }

    /**
     * To set the given translation for a particular language
     * @param lang language to set
     * @param text translation
     */
    public void setLang(AppLang lang, String text) {
        if (lang == null) {
            // No language: cannot perform operation
            return;
        }
        final String textToSet = StringUtils.isNotBlank(text) ? text.trim() : null;

        switch (lang) {
            case FRENCH: this.french = textToSet; break;
            case ENGLISH: this.english = textToSet; break;
            case CHINESE: this.chinese = textToSet; break;
            default:
                // do nothing
                break;
        }
    }

    /**
     * To get the given translation for a particular language
     * @param lang language to retrieve
     * @return corresponding translation or null
     */
    public String getLang(AppLang lang) {
        if (lang == null) {
            // No language: cannot perform operation
            return null;
        }

        String textToReturn = null;
        switch (lang) {
            case FRENCH: textToReturn = this.french ; break;
            case ENGLISH: textToReturn = this.english; break;
            case CHINESE: textToReturn = this.chinese; break;
            default:
                // do nothing
                break;
        }

        return textToReturn;
    }
}
