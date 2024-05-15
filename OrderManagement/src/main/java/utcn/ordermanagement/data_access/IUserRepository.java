package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.ICrudRepository;
import utcn.ordermanagement.models.User;

import java.sql.SQLException;

public interface IUserRepository extends ICrudRepository<User, Long> {
    User findByUsername(String username) throws SQLException;
}
