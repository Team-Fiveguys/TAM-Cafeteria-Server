package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Diet {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // 나중에 과거의 식단까지 볼 수 있도록 확장하면 날짜로 수정할 예정

    private String image; //이미지 테이블 만들어야 함

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cafeteria cafeteria;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuDiet> menuDietList;
}
