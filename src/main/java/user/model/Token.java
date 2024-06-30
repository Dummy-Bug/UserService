package user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Token extends BaseModel {
    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;
}
