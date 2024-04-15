package fiveguys.Tom.Cafeteria.Server.domain.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MenuRequestDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class MenuEnrollDTO{
        private String name;
        private Long cafeteriaId;
    }
}
