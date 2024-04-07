package fiveguys.Tom.Cafeteria.Server.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Menu {
    @Id
    private Long id;

    private String name;

    private boolean isMain;

    private int likeCount;

    @JoinColumn(name = "menu_category_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MenuCategory menuCategory;
}
