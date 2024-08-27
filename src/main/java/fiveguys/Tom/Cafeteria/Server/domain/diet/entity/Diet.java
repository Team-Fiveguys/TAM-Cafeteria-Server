package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Entity
public class Diet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    private boolean soldOut;

    private boolean dayOff;

    private LocalDate localDate;

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Cafeteria cafeteria;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MenuDiet> menuDietList;

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

    public void switchSoldOut(){
        this.soldOut = this.soldOut ? false : true;
    }

    public void switchDayOff(){
        this.dayOff = this.dayOff ? false : true;
    }

    public String getMenuListString(){
        StringBuffer menuString = new StringBuffer();
        getMenuDietList().stream()
                .map(MenuDiet::getMenu)
                .forEach(menu -> menuString.append(menu.getName() + "\n" ) );
        return menuString.toString();
    }

    public static boolean isDayOffOrNull(Diet diet){
        if(diet == null || diet.isDayOff()){
            return true;
        }

        return false;
    }
}
