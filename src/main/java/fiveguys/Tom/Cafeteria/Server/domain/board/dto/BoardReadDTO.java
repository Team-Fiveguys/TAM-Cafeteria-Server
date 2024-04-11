package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardReadDTO {
    private Long id;
    private String user;
    private String title;
    private String content;
    private String boardType;
    private int likeCount;
    private boolean isAdminPick;
}
