package fiveguys.Tom.Cafeteria.Server.domain.board.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private int likeCount;

    private boolean isAdminPick;

}
