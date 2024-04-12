package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class DietCreateResponseDTO {
    private Long dietId;
    private Long cafeteriaId;
}
