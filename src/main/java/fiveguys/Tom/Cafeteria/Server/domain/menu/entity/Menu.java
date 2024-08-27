package fiveguys.Tom.Cafeteria.Server.domain.menu.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Menu extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private boolean isMain;

    private int likeCount;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "cafeteria_id")
    private Cafeteria cafeteria;

    @JoinColumn(name = "menu_category_id")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MenuCategory menuCategory;
}
