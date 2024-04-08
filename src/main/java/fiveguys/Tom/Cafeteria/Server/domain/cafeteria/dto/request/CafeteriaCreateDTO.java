package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.dto.request;


import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CafeteriaCreateDTO {
    private String name;
    private String address;
    private String hour;
}
