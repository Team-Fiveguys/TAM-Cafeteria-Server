package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import fiveguys.Tom.Cafeteria.Server.domain.board.entity.BoardType;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponseDTO {
    private Long id;
    private String title;
    private String content;
    private BoardType boardType;
    // 기타 필요한 필드

}
