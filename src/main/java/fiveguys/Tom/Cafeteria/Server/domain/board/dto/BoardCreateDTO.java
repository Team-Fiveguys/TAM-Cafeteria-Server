package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardCreateDTO {
    private BoardType boardType;
    private Long userId;
    private String title;
    private String content;

}