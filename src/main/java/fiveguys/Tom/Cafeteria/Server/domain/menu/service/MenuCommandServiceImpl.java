package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.repository.MenuRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MenuCommandServiceImpl implements MenuCommandService{
    private final MenuRepository menuRepository;
    private final MenuQueryService menuQueryService;
    @Override
    public Long enroll(Menu menu) {
        if(menuQueryService.existByName(menu.getName())){
            throw new GeneralException(ErrorStatus.MENU_DUPLICATE);
        }
        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void remove(Menu menu) {
        menuRepository.delete(menu);
    }
}
