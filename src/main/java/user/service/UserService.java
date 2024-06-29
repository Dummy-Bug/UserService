package user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import user.model.Token;
import user.model.User;
import user.repository.UserRepository;

@Service
public class UserService {

    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository,BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signUp(String email, String name, String password) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(false);
        return userRepository.save(user);
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
