package fiveguys.Tom.Cafeteria.Server.domain.menu.service;

import fiveguys.Tom.Cafeteria.Server.apiPayload.code.status.ErrorStatus;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import fiveguys.Tom.Cafeteria.Server.domain.menu.repository.MenuRepository;
import fiveguys.Tom.Cafeteria.Server.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MenuQueryServiceImpl implements MenuQueryService{
    private final MenuRepository menuRepository;
    @Override
    public Menu findById(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(
                () -> new GeneralException(ErrorStatus.MENU_NOT_FOUND)
        );
        return menu;
    }
}
