package eu.daxiongmao.core.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * Application user
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "firstName", "lastName", "langCode", "username", "email", "phoneNumber", "status", "isActive", "emailConfirmationDate", "passwordLastChangeDate" })
@EqualsAndHashCode(of = {"username", "email", "langCode"})
@NoArgsConstructor
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 20191205L;

    /** First name */
    @Max(100)
    private String firstName;

    /** Last name */
    @Max(200)
    private String lastName;

    /** Preferred language (ex: EN, FR, DE, etc.) */
    @NotBlank
    @Max(2)
    private String langCode;

    /** User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username
     * It is always in UPPER case */
    @NotBlank
    @Max(50)
    private String username;
    public void setUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            this.username = username.trim().toUpperCase();
        } else {
            this.username = null;
        }
    }

    /** User email. MANDATORY.
     * This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application.
     * It is always in LOWER case
     */
    @NotBlank
    @Max(255)
    @Email
    private String email;
    public void setEmail(String email) {
        if (email != null) {
            this.email = email.trim().toLowerCase();
        } else {
            this.email = null;
        }
    }

    /** User phone number. If provided it must include the country code. ex: +352 for Luxembourg ; +33 for France */
    @NotBlank
    @Max(20)
    private String phoneNumber;

    /** User status. MANDATORY. This represents his current status if enabled */
    @NotBlank
    @Max(255)
    private String status;

    /** To know when the user confirmed his email - if he did so. This is required for anti-phishing reasons */
    private Date emailConfirmationDate;

    /** Date time of the last password change. This is important to notify user to change his password periodically for security reasons */
    private Date passwordLastChangeDate;

    /** Boolean flag. MANDATORY. "1" to use the object, "0" to block usage */
    private Boolean isActive;

    /** Object version. This is incremented at each operation */
    private Long version;

}

