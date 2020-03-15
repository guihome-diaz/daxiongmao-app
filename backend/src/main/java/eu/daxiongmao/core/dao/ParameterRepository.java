package eu.daxiongmao.core.dao;

import eu.daxiongmao.core.model.db.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * To interact with "parameters" table
 * @author Guillaume Diaz
 * @version 1.0 2019/11
 * @since application creation
 */
@Repository
public interface ParameterRepository extends JpaRepository<Parameter, Long> {

    /**
     * To find a parameter by its name
     * @param paramName search parameter name
     * @return corresponding parameter
     */
    Optional<Parameter> findParameterByParamName(String paramName);

}
