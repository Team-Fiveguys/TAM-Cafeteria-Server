package fiveguys.Tom.Cafeteria.Server.domain.menu.dto;


import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


public class MenuResponseDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MenuEnrollDTO{
        private Long menuId;
    }
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MenuQueryDTO{
        private Long menuId;
        private String name;
    }
    @Builder
    @AllArgsConstructor
    @Getter
    public static class MenuResponseListDTO {
        private List<MenuQueryDTO> menuQueryDTOList;
    }
}
