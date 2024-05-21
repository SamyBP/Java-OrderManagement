package utcn.ordermanagement.data_access;

import utcn.ordermanagement.data_access.repository.CrudRepository;
import utcn.ordermanagement.data_access.repository.Query;
import utcn.ordermanagement.models.User;

import java.sql.SQLException;

public class UserRepository extends CrudRepository<User, Long> implements IUserRepository{
    @Override
    public User findByUsername(String username) throws SQLException {
        return Query.select()
                .from(User.class)
                .where("username = ?", username)
                .firstOrDefault(User.class);
    }
}
