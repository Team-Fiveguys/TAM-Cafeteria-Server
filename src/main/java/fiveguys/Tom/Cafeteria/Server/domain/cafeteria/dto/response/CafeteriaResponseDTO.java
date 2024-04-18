package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response;


import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Congestion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


public class CafeteriaResponseDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class CreateResponseDTO {
        private Long cafeteriaId;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class SetCongestionResponseDTO {
        private Congestion congestion;
    }
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class QueryCongestionResponseDTO {
        private Congestion congestion;
    }
}
