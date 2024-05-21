package utcn.ordermanagement.services;

import org.mindrot.jbcrypt.BCrypt;
import utcn.ordermanagement.data_access.IUserRepository;
import utcn.ordermanagement.models.User;

import java.sql.SQLException;

public class AuthenticationService {

    private final IUserRepository userRepository;

    public AuthenticationService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean login(String username, String password) throws SQLException, InvalidCredentialsException {
        User user = userRepository.findByUsername(username);

        if (user == null)
            throw new InvalidCredentialsException("Wrong username");

        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new InvalidCredentialsException("Wrong password");

        return true;
    }

    public void register(String username, String password) throws SQLException, UsernameAlreadyTakenException {
        if (userRepository.findByUsername(username) != null)
            throw new UsernameAlreadyTakenException("Username already taken");

        User user = new User(
                username,
                BCrypt.hashpw(password, BCrypt.gensalt())
        );

        userRepository.save(user);
    }

}
