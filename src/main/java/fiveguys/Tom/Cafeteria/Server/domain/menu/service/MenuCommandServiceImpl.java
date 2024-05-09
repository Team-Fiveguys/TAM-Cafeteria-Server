package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.service.CafeteriaQueryService;
import fiveguys.Tom.Cafeteria.Server.domain.menu.converter.MenuConverter;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.repository.MenuRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuCommandServiceImpl implements MenuCommandService{
    private final MenuRepository menuRepository;
    private final MenuQueryService menuQueryService;
    private final CafeteriaQueryService cafeteriaQueryService;
    @Override
    @Transactional
    public Long enroll(Long cafeteriaId, String menuName) {
        if(menuQueryService.existByNameAndCafeteria(cafeteriaId, menuName)) {
            throw new GeneralException(ErrorStatus.MENU_DUPLICATE);
        }

        Cafeteria cafeteria = cafeteriaQueryService.findById(cafeteriaId);
        Menu menu = MenuConverter.toMenu(cafeteria, menuName);
        menuRepository.save(menu);
        return menu.getId();
    }

    @Override
    public void remove(Menu menu) {
        menuRepository.delete(menu);
    }
}
