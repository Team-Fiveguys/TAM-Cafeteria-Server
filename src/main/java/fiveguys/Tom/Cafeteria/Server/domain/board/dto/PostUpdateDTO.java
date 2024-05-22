package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PostUpdateDTO {
    private String title;
    private String content;
}
