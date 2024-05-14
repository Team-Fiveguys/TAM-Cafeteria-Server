package fiveguys.Tom.Cafeteria.Server.domain.diet.repository;

import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.Diet;
import fiveguys.Tom.Cafeteria.Server.domain.diet.entity.MenuDiet;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuDietRepository extends JpaRepository<MenuDiet, Long> {
    public MenuDiet findFirstByDietAndMenu(Diet diet, Menu menu);
}
