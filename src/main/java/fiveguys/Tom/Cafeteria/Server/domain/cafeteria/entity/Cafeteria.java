package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity;


import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Cafeteria extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address; //식당 위치?

    @Enumerated(EnumType.STRING)
    private Congestion congestion;

    private LocalTime breakfastStartTime; //조식 운영 시작 시간

    private LocalTime breakfastEndTime; //조식 운영 시작 시간

    private LocalTime lunchStartTime; //중식 운영 시작 시간

    private LocalTime lunchEndTime; //중식 운영 시작 시간

    public void setCongestion(Congestion congestion) {
        this.congestion = congestion;
    }
}
