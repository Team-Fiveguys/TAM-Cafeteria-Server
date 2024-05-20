package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostCreateDTO {
    private BoardType boardType;
    private String title;
    private String content;
    private Long cafeteriaId;
}