package fiveguys.Tom.Cafeteria.Server.domain.menu.dto;

import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;


@Builder
@AllArgsConstructor
@Getter
public class MenuResponseListDTO {
    private List<Menu> menuResponseDTOList;

}
