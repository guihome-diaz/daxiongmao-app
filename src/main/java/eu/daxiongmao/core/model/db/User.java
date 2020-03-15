package eu.daxiongmao.core.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * Application user
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(callSuper = true, of = { "id", "firstName", "lastName", "langCode", "username", "email", "phoneNumber", "status", "activationKey", "emailConfirmationDate", "passwordHash", "passwordSalt", "passwordAlgorithm", "passwordLastChangeDate" })
@EqualsAndHashCode(callSuper = true, of = {"username", "email", "passwordSalt", "passwordAlgorithm", "langCode"})
@Entity
@Table(name = "USERS", indexes = {
        // index by full name
        @Index(name = "USERS_NAME_IDX", unique = true, columnList = "FIRST_NAME, SURNAME"),
        // indexes by username
        @Index(name = "USERS_USERNAME_IDX", unique = true, columnList = "USERNAME"),
        @Index(name = "USERS_ACTIVE_USERNAME_IDX", columnList = "USERNAME, IS_ACTIVE"),
        // indexes by email
        @Index(name = "USERS_EMAIL_IDX", unique = true, columnList = "EMAIL"),
        @Index(name = "USERS_ACTIVE_EMAIL_IDX", columnList = "EMAIL, IS_ACTIVE")
})
public class User extends GenericEntity {

    private static final long serialVersionUID = 20191205L;

    @Id
    @Column(name = "USER_ID")
    @SequenceGenerator(name = "seqUsers", sequenceName = "SEQ_USERS", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqUsers")
    private Long id;

    /** First name */
    @Max(100)
    @Column(name = "FIRST_NAME", length = 100)
    private String firstName;

    /** Last name */
    @Max(200)
    @Column(name = "SURNAME", length = 200)
    private String lastName;

    /** Preferred language (ex: EN, FR, DE, etc.) */
    @NotBlank
    @Max(2)
    @Column(name = "LANG_CODE", nullable = false, length = 2)
    private String langCode;

    /** User login. MANDATORY. This must be unique, even if the user is disabled no one can take his username.
     *  OWASP principles, username must be unique and not case sensitive.
     *  Always in UPPER case for this application
     */
    @NotBlank
    @Max(50)
    @Column(name = "USERNAME", nullable = false, length = 50)
    private String username;
    public void setUsername(String username) {
        if (username != null) {
            this.username = username.trim().toUpperCase();
        } else {
            this.username = null;
        }
    }

    /** User email. MANDATORY. This must be unique both in EMAIL and BACKUP_EMAIL columns. User must validate his email before using the application
     *  OWASP principles, email must be unique and not case sensitive
     *  Always in LOWER case for this application */
    @NotBlank
    @Max(255)
    @Email
    @Column(name = "EMAIL", nullable = false, length = 255)
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
    @Column(name = "PHONE_NUMBER", nullable = false, length = 20)
    private String phoneNumber;

    /** User status. MANDATORY. This represents his current status if enabled */
    @NotBlank
    @Max(255)
    @Column(name = "STATUS", nullable = false, length = 255)
    private String status;

    /** Activation key. MANDATORY. This is required to confirm the user registration and activate the account */
    @NotBlank
    @Max(255)
    @Column(name = "ACTIVATION_KEY", nullable = false, length = 255)
    private String activationKey;

    /** To know when the user confirmed his email - if he did so. This is required for anti-phishing reasons */
    @Column(name = "DATE_EMAIL_CONFIRMED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailConfirmationDate;

    /** User password. This can be let NULL if user wants to validate his email first. Important: like all modern apps, we do NOT store the user password */
    @Column(name = "PASSWORD_HASH", length = 255)
    private String passwordHash;

    /** Security salt. MANDATORY. (random value) that is required to compute the user password hash. Every user have different salts' */
    @NotBlank
    @Max(255)
    @Column(name = "PASSWORD_SALT", nullable = false, length = 255)
    private String passwordSalt;

    /** Password hash algorithm. MANDATORY. This is required to compute the password hash. Security can change over time, that is why we must store the algorithm used for each user */
    @NotBlank
    @Max(50)
    @Column(name = "PASSWORD_ALGORITHM", nullable = false, length = 50)
    private String passwordAlgorithm;

    /** Date time of the last password change. This is important to notify user to change his password periodically for security reasons */
    @Column(name = "PASSWORD_LAST_CHANGE")
    private Date passwordLastChangeDate;

    public User() {
        super();
    }
}

