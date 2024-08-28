package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity;


import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import fiveguys.Tom.Cafeteria.Server.domain.user.entity.UserCafeteria;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Cafeteria extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String location; //식당 위치?

    @Enumerated(EnumType.STRING)
    private Congestion congestion;

    private boolean runLunch;

    private boolean runBreakfast;

    private LocalTime breakfastStartTime; //조식 운영 시작 시간

    private LocalTime breakfastEndTime; //조식 운영 시작 시간

    private LocalTime lunchStartTime; //중식 운영 시작 시간

    private LocalTime lunchEndTime; //중식 운영 시작 시간

    @OneToMany(mappedBy = "cafeteria", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserCafeteria> userCafeteriaList = new ArrayList<>();

    public void setCongestion(Congestion congestion) {
        this.congestion = congestion;
    }
}
