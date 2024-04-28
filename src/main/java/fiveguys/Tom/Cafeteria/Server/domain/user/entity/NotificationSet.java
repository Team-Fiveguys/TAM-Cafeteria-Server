package fiveguys.Tom.Cafeteria.Server.domain.user.entity;

import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationToken;

    @Column(columnDefinition = "boolean default true")
    private boolean todayDiet;
    @Column(columnDefinition = "boolean default true")
    private boolean weekDiet;
    @Column(columnDefinition = "boolean default true")
    private boolean soldOut;
    @Column(columnDefinition = "boolean default true")
    private boolean dietModification;
}
