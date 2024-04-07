package fiveguys.Tom.Cafeteria.Server.entity;


import fiveguys.Tom.Cafeteria.Server.entity.Enum.BoardType;
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
    private Long id;

    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    private int likeCount;

    private boolean isAdminPick;

}
