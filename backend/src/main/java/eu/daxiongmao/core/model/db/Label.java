package eu.daxiongmao.core.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
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
@ToString(callSuper = true, of = { "code", "english" })
@EqualsAndHashCode(callSuper = true, of = {"code"})
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
    public void setChinese(String zhText) {
        if (StringUtils.isNotBlank(zhText)) {
            this.chinese = zhText.trim();
        } else {
            this.chinese = null;
        }
    }

    @Max(2000)
    @Column(name = "LANG_EN", length = 2000)
    private String english;
    public void setEnglish(String enText) {
        if (StringUtils.isNotBlank(enText)) {
            this.english = enText.trim();
        } else {
            this.english = null;
        }
    }

    @Max(2000)
    @Column(name = "LANG_FR", length = 2000)
    private String french;
    public void setFrench(String frText) {
        if (StringUtils.isNotBlank(frText)) {
            this.french = frText.trim();
        } else {
            this.french = null;
        }
    }

    public Label() {
        super();
    }

}
