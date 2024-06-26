package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;


import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MenuDiet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "menu_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Menu menu;

    @JoinColumn(name = "diet_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Diet diet;

    public static MenuDiet createMenuDiet(Menu menu, Diet diet){
        MenuDiet menuDiet = new MenuDiet();
        menuDiet.setDiet(diet);
        menuDiet.setMenu(menu);
        diet.getMenuDietList().add(menuDiet);
        return menuDiet;
    }
}
