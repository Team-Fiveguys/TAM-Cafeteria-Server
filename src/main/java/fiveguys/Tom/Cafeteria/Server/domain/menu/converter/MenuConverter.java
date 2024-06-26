package fiveguys.Tom.Cafeteria.Server.domain.menu.converter;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.dto.MenuResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;

import java.util.List;

public class MenuConverter {
    public static Menu toMenu(Cafeteria cafeteria, String menuName){
        return Menu.builder()
                .name(menuName)
                .cafeteria(cafeteria)
                .build();
    }
    public static MenuResponseDTO.MenuResponseListDTO toMenuResponseListDTO(List<MenuResponseDTO.MenuQueryDTO> menuQueryDTOList){
        return MenuResponseDTO.MenuResponseListDTO.builder()
                .menuQueryDTOList(menuQueryDTOList)
                .build();
    }
    public static MenuResponseDTO.MenuEnrollDTO toEnrollResponseDTO(Long menuId){
        return MenuResponseDTO.MenuEnrollDTO.builder()
                .menuId(menuId)
                .build();
    }
    public static MenuResponseDTO.MenuQueryDTO toMenuQueryDTO(Menu menu){
        return MenuResponseDTO.MenuQueryDTO.builder()
                .menuId(menu.getId())
                .name(menu.getName())
                .build();
    }
}
