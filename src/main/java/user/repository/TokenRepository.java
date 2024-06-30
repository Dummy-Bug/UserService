package user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import user.model.Token;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {


    Optional<Token> findByValueAndDeleted(String tokenValue, boolean deleted);

    /*

    select * from tokens where value = tokenValue and deleted = false and expiry_date > time.
     */
    Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String tokenValue, boolean deleted, Date currentTime);
}
