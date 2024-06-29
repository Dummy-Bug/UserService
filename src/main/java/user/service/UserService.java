package user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import user.exception.UserNotFoundException;
import user.model.Token;
import user.model.User;
import user.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public User signUp(String email, String name, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));
        user.setEmailVerified(false);
        return userRepository.save(user);
    }

    public Token login(String email, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User does not exist");
        }
        if (!bCryptPasswordEncoder.matches(password, optionalUser.get().getHashedPassword())) {
            throw new Exception("password is incorrect");
        }
        return Token.builder()
                .value("Login Success")
                .build();
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
