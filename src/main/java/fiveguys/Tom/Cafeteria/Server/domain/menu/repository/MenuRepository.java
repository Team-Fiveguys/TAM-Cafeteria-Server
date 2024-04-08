package fiveguys.Tom.Cafeteria.Server.domain.menu.repository;

import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
}
