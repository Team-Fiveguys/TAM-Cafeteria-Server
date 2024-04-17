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
    private BoardType boardType;
    private Long userId;
    private String title;
    private String content;
    private int likeCount;
    private boolean isAdminPick;

}
