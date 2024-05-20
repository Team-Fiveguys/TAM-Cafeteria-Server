package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardDeleteDTO {
    private Long userId;
    private Long id;
}

