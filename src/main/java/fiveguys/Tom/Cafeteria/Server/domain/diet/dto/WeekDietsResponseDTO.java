package fiveguys.Tom.Cafeteria.Server.domain.diet.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeekDietsResponseDTO {
    private List<DietResponseDTO> dietResponseDTOList;
}
