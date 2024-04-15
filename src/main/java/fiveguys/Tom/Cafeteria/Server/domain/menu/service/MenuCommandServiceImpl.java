package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuCommandServiceImpl implements MenuCommandService{
    private final MenuRepository menuRepository;
    @Override
    public Long enroll(Menu menu) {
        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void remove(Menu menu) {
        menuRepository.delete(menu);
    }
}
