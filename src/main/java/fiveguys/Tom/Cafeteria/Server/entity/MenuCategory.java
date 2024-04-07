package fiveguys.Tom.Cafeteria.Server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class MenuCategory {
    @Id
    private Long id;

    private String name;
}
