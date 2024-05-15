package utcn.ordermanagement.services;

public class UsernameAlreadyTakenException extends Exception{
    public UsernameAlreadyTakenException(String message) {
        super(message);
    }
}
