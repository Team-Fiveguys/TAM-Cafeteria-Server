package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.converter;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.request.CafeteriaCreateDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaCreateResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;

public class CafeteriaConverter {
    public static Cafeteria toCafeteria(CafeteriaCreateDTO cafeteriaCreateDTO){
        Cafeteria newCafeteria = Cafeteria.builder()
                .address(cafeteriaCreateDTO.getAddress())
                .name(cafeteriaCreateDTO.getName())
                .build();
        return newCafeteria;
    }
    public static CafeteriaCreateResponseDTO toCafeteriaResponse(Cafeteria cafeteria){
        return CafeteriaCreateResponseDTO.builder()
                .build();

    }
}
