package fiveguys.Tom.Cafeteria.Server.domain.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDTO {
    private Long id;
    private String boardType;
    private String User;
    private String title;
    private String content;
}
