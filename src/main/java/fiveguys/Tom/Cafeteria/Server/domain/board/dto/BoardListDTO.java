package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardListDTO {
    private Long id;
    private String title;

    //DTO 수정하고 Controller, Service 수정해야함
}
