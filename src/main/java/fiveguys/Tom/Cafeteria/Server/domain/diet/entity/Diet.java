package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek; // 나중에 과거의 식단까지 볼 수 있도록 확장하면 날짜로 수정할 예정

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cafeteria cafeteria;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuDiet> menuDietList;

    @OneToOne(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DietPhoto dietPhoto;

    public void setCafeteria(Cafeteria cafeteria) {
        this.cafeteria = cafeteria;
    }
    public void addMenu(Menu menu){
        MenuDiet menuDiet = MenuDiet.createMenuDiet(menu, this);
        this.menuDietList.add(menuDiet);
    }
    public void remove(MenuDiet menuDiet){
        this.menuDietList.remove(menuDiet);
        menuDiet.setDiet(null);
    }
}
