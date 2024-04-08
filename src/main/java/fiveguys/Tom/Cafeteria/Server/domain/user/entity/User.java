package fiveguys.Tom.Cafeteria.Server.domain.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class User{
    @Id
    private Long id;

    private String sex;

    private String role;

    private String name;
}
