package eu.daxiongmao.core.dao;

import eu.daxiongmao.core.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * To interact with "users" table
 * @author Guillaume Diaz
 * @version 1.0 2019/11
 * @since application creation
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
