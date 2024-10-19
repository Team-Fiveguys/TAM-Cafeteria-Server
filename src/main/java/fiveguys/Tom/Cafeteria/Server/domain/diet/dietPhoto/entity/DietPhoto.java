package fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity;


import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DietPhoto extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageKey;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diet_id")
    private Diet diet;

    public void changeImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}
