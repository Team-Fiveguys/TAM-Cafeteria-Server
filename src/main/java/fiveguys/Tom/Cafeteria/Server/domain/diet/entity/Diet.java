package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Diet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    private boolean soldOut;

    private LocalDate localDate; // 나중에 과거의 식단까지 볼 수 있도록 확장하면 날짜로 수정할 예정

    private int year;

    private int month;

    private int week;

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cafeteria cafeteria;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuDiet> menuDietList = new ArrayList<>();

    @OneToOne(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DietPhoto dietPhoto;

    public void setCafeteria(Cafeteria cafeteria) {

        this.cafeteria = cafeteria;
    }

    public void addMenu(Menu menu){
        MenuDiet menuDiet = MenuDiet.createMenuDiet(menu, this);
        this.menuDietList.add(menuDiet);
    }
    public void remove(MenuDiet menuDiet){
        this.menuDietList.remove(menuDiet);
        menuDiet.setDiet(null);
    }

    public void switchSoldOut(){
        this.soldOut = this.soldOut ? false : true;
    }

    public void setDateInfo(){
        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        // 시스템의 기본 Locale을 사용하여 WeekFields를 가져옵니다.
        // 여기서는 대한민국 기준으로 월요일이 한 주의 시작이라고 가정합니다.
        // 다른 Locale이 필요한 경우 Locale을 변경하세요.
        LocalDate thursday = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        // 월의 주차 정보를 가져오기 위한 TemporalField
        TemporalField weekOfMonth = weekFields.weekOfMonth();

        // 월의 주차 계산
        int weekNumber = thursday.get(weekOfMonth);

        this.week = weekNumber;
        System.out.println(localDate + "는 " + thursday.getMonthValue() + "월의 " + weekNumber + "주차 입니다.");
    }
}
