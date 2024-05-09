package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.util.List;

public interface MenuQueryService {
    public Menu findById(Long id);

    public List<Menu> getAllMenu(Cafeteria cafeteria);

    public Menu findByName(String name);

    public boolean existByNameAndCafeteria(Long cafeteriaId ,String name);
}
