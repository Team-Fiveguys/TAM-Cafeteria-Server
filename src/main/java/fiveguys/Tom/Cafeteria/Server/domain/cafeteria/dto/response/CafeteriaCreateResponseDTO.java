package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CafeteriaCreateResponseDTO {
    private Long cafeteriaId;
}
