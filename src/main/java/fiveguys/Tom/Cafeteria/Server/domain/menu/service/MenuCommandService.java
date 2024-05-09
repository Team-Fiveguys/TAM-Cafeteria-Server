package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

public interface MenuCommandService {
    public Long enroll(Long cafeteriaId, String menu);
    public void remove(Menu menu);

}
