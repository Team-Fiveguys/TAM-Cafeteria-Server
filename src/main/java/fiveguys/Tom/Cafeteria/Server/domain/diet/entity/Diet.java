package fiveguys.Tom.Cafeteria.Server.domain.diet.entity;

import fiveguys.Tom.Cafeteria.Server.domain.cafeteria.entity.Cafeteria;
import fiveguys.Tom.Cafeteria.Server.domain.common.BaseEntity;
import fiveguys.Tom.Cafeteria.Server.domain.diet.dietPhoto.entity.DietPhoto;
import fiveguys.Tom.Cafeteria.Server.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
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
public class Diet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Meals meals;

    private boolean soldOut;

    private boolean dayOff;

    private LocalDate localDate; // 나중에 과거의 식단까지 볼 수 있도록 확장하면 날짜로 수정할 예정

    private int year;

    private int month;

    private int week;

    @JoinColumn(name = "cafeteria_id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Cafeteria cafeteria;

    @OneToMany(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MenuDiet> menuDietList = new ArrayList<>();

    @OneToOne(mappedBy = "diet", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
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

    public void switchDayOff(){
        this.dayOff = this.dayOff ? false : true;
    }

    public void setDateInfo(){
        this.year = localDate.getYear();
        this.month = localDate.getMonthValue();
        // 주의 시작을 월요일로 설정
        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

        // 월의 주차 정보를 가져오기 위한 TemporalField
        TemporalField weekOfMonth = weekFields.weekOfMonth();

        // 현재 주의 월요일 날짜를 구함
        LocalDate monday = localDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        // 현재 주의 목요일 날짜를 구함
        LocalDate thursday;
        if( localDate.getDayOfWeek().getValue() <= DayOfWeek.THURSDAY.getValue()){
            //월,화,수,목은 다음 목요일 날짜를
            thursday = localDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
        }
        else{
            // 금,토,일은 이전 목요일 날짜를
            thursday = localDate.with(TemporalAdjusters.previous(DayOfWeek.THURSDAY));
        }
        // 월요일과 목요일이 같은 달에 속하지 않는 경우, 주차 계산을 위해 목요일을 사용
        if (monday.getMonth() != thursday.getMonth()) {
            // 목요일이 다음 달에 속하면, 그 주는 다음 달의 첫째 주로 간주
            // 목요일의 주차 정보를 이용
            this.week = thursday.get(weekOfMonth);
            Month thursdayMonth = thursday.getMonth();
            if( this.month < thursdayMonth.getValue()){
                this.month += 1;
                if( this.month > 12){
                    this.month = 1;
                    this.year += 1;
                }
            }
        } else {
            // 그렇지 않으면, 원래 날짜의 주차 정보를 이용
            this.week = monday.get(weekOfMonth);
        }
        System.out.println(localDate + "는 " + thursday.getMonthValue() + "월의 " + this.week+ "주차 입니다.");
    }
}
