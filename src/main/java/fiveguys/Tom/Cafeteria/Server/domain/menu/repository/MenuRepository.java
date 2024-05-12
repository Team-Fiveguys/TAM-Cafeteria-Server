package fiveguys.Tom.Cafeteria.Server.domain.menu.repository;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    public List<Menu> findAllByCafeteria(Cafeteria cafeteria);

    public boolean existsByCafeteriaAndName(Cafeteria cafeteria, String name);

    public Optional<Menu> findByCafeteriaAndName(Cafeteria cafeteria, String name);
}
