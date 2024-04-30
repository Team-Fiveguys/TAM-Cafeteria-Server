package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity;


import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class DietPhoto{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageKey;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "diet_id")
    private Diet diet;

}
