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
    private boolean hakGwan;
    @Column(columnDefinition = "boolean default true")
    private boolean myeongJin;
    @Column(columnDefinition = "boolean default true")
    private boolean myeongDon;
    @Column(columnDefinition = "boolean default true")
    private boolean todayDiet;
    @Column(columnDefinition = "boolean default true")
    private boolean dietPhotoEnroll;
    @Column(columnDefinition = "boolean default true")
    private boolean weekDietEnroll;
    @Column(columnDefinition = "boolean default true")
    private boolean dietSoldOut;
    @Column(columnDefinition = "boolean default true")
    private boolean dietChange;
}
