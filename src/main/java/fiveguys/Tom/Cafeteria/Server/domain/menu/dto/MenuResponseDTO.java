package fiveguys.Tom.Cafeteria.Server.domain.menu.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class MenuResponseDTO {
    private String name;
}
