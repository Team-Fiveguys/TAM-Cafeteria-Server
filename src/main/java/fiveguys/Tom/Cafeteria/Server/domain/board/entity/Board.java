package fiveguys.Tom.Cafeteria.Server.domain.board.entity;


import fiveguys.Tom.Cafeteria.Server.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Board {
    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private BoardType boardType;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;

    @Lob
    private String content;

    private int likeCount;

    private boolean isAdminPick;

}
