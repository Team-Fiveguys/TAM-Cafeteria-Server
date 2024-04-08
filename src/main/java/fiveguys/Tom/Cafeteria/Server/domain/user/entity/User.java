package fiveguys.Tom.Cafeteria.Server.domain.user.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private String role;

    private String name;
}
