package fiveguys.Tom.Cafeteria.Server.entity;

import fiveguys.Tom.Cafeteria.Server.entity.Enum.Meals;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Diet {
    @Id
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    private LocalDate localDate;

    private String image; //이미지 테이블 만들어야 함

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cafeteria cafeteria;
}
