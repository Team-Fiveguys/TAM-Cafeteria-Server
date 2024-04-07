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
public class Cafeteria {
    @Id
    private Long id;

    private String name;

    private String address; //식당 위치?

    private String hour; //운영 시간
}
