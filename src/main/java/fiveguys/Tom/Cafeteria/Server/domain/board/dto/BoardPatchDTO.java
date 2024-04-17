package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardPatchDTO {
    private String title;
    private String content;
    private int likeCount;
    private boolean isAdminPick;
}
