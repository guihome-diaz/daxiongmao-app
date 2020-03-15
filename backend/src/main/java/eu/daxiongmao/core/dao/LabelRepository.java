package eu.daxiongmao.core.dao;

import eu.daxiongmao.core.model.db.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * To interact with "labels" table
 * @author Guillaume Diaz
 * @version 1.0 2020/03
 * @since application creation
 */
@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    /**
     * To retrieve a label by its code
     * @param code search code
     * @return corresponding label
     */
    Optional<Label> findByCode(String code);
}
