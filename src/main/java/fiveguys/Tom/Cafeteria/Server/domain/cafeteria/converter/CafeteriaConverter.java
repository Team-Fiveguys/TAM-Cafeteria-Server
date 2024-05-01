package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.converter;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.request.CafeteriaRequestDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.response.CafeteriaResponseDTO;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Congestion;

public class CafeteriaConverter {
    public static Cafeteria toCafeteria(CafeteriaRequestDTO.CafeteriaCreateDTO cafeteriaCreateDTO){
        Cafeteria newCafeteria = Cafeteria.builder()
                .location(cafeteriaCreateDTO.getLocation())
                .runBreakfast(cafeteriaCreateDTO.isRunBreakfast())
                .runLunch(cafeteriaCreateDTO.isRunLunch())
                .breakfastStartTime(cafeteriaCreateDTO.getBreakfastStartTime())
                .breakfastEndTime(cafeteriaCreateDTO.getBreakfastEndTime())
                .lunchStartTime(cafeteriaCreateDTO.getLunchStartTime())
                .lunchEndTime(cafeteriaCreateDTO.getLunchEndTime())
                .name(cafeteriaCreateDTO.getName())
                .build();
        return newCafeteria;
    }
    public static CafeteriaResponseDTO.CreateResponseDTO toCafeteriaResponse(Cafeteria cafeteria){
        return CafeteriaResponseDTO.CreateResponseDTO.builder()
                .cafeteriaId(cafeteria.getId())
                .build();
    }

    public static CafeteriaResponseDTO.SetCongestionResponseDTO toSetCongestionResponseDTO(Congestion congestion){
        return CafeteriaResponseDTO.SetCongestionResponseDTO.builder()
                .congestion(congestion)
                .build();
    }
    public static CafeteriaResponseDTO.QueryCongestionResponseDTO toQueryCongestionResponseDTO(Congestion congestion){
        return CafeteriaResponseDTO.QueryCongestionResponseDTO.builder()
                .congestion(congestion)
                .build();
    }

    public static CafeteriaResponseDTO.QueryRunResponseDTO toQueryRunResponseDTO(Cafeteria cafeteria){
        return CafeteriaResponseDTO.QueryRunResponseDTO.builder()
                .runBreakfast(cafeteria.isRunBreakfast())
                .runLunch(cafeteria.isRunLunch())
                .build();
    }
}
