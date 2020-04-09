package eu.daxiongmao.core.model.db;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Generic entity that contains attributes for all application's objects.
 * @version 1.0 - 2019/12
 * @author Guillaume Diaz
 * @since version 1.0
 */
@Getter
@Setter
@ToString(of = { "isActive", "creationDate", "modificationDate", "version" })
@EqualsAndHashCode(of = {"creationDate"})
@MappedSuperclass
public class GenericEntity implements Serializable {

    private static final long serialVersionUID = 20191205L;

    /** Object creation date-time */
    @Column(name = "CREATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate = new Date();

    /** Object version. This is incremented at each operation */
    @Version
    @Column(name = "VERSION", nullable = false)
    private long version = 1L;

    /** Object last modification date-time */
    @Column(name = "MODIFICATION_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modificationDate = new Date();

    /** Boolean flag. MANDATORY. "1" to use the object, "0" to block usage */
    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive = false;

}
