package user.service;

import org.springframework.stereotype.Service;
import user.dto.UserDto;
import user.model.Token;
import user.model.User;

@Service
public class UserService {

    public User signUp(String email, String name, String password) {
        return null;
    }

    public Token login(String email, String password) {
        return null;
    }

    private Token generateToken(User user) {
        return null;
    }

    public void logout(String tokenValue) {

    }

    public User validateToken(String token) {
        return null;
    }
}
