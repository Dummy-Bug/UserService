package user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Builder
@Getter
@Setter
@Entity
public class Token extends BaseModel {
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
}
