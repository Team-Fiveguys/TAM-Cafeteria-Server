package fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Cafeteria {
    @Id
    private Long id;

    private String name;

    private String address; //식당 위치?

    private LocalTime breakfastStartTime; //조식 운영 시작 시간

    private LocalTime breakfastEndTime; //조식 운영 시작 시간

    private LocalTime lunchStartTime; //중식 운영 시작 시간

    private LocalTime lunchEndTime; //중식 운영 시작 시간
}
