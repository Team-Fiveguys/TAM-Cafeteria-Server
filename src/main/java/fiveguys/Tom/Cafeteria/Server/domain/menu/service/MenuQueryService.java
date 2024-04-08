package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

public interface MenuQueryService {
    public Menu findById(Long id);
}
