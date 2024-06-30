package user.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import user.exception.UserNotFoundException;
import user.model.Token;
import user.model.User;
import user.repository.TokenRepository;
import user.repository.UserRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    UserRepository userRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    TokenRepository tokenRepository;

    UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;
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
        Token token = generateToken(optionalUser.get());
        return tokenRepository.save(token);
    }

    private Token generateToken(User user) {
        LocalDate currentDate = LocalDate.now();
        LocalDate thirtyDaysLater = currentDate.plusDays(30);

        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Token.builder()
                .value(UUID.randomUUID().toString())
                .user(user)
                .expiryAt(expiryDate)
                .build();
    }

    public void logout(String tokenValue) throws Exception {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeleted(tokenValue, false);

        if (optionalToken.isEmpty()) {
            throw new Exception("not able to logout please try again");
        }

        Token token = optionalToken.get();
        token.setDeleted(true);
        tokenRepository.save(token);
    }

    public User validateToken(String token) throws Exception {
        Optional<Token> optionalToken = tokenRepository.findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());

        if (optionalToken.isEmpty()) {
            System.out.println("Token is Invalid");
            throw new Exception("Invalid Token");
        }
        System.out.println("Token is valid");
        return optionalToken.get().getUser();
    }
}
