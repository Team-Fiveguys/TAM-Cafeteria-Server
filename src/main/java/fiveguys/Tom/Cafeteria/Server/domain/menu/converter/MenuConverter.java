package fiveguys.Tom.Cafeteria.Server.domain.menu.converter;

import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseListDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.util.List;

public class MenuConverter {
    public static MenuResponseDTO toMenuResponseDTO(Menu menu){
        return MenuResponseDTO.builder()
                .name(menu.getName())
                .build();
    }
    public static MenuResponseListDTO toMenuResponseListDTO(List<Menu> menuResponseDTOList){
        return MenuResponseListDTO.builder()
                .menuResponseDTOList(menuResponseDTOList)
                .build();
    }
}
