package fiveguys.Tom.Cafeteria.Server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.Locale;


class TomCafeteriaServerApplicationTests {

	@Test
	void contextLoads() {
		// 특정 날짜 설정
		LocalDate date = LocalDate.of(2024, 1, 30);

		// 시스템의 기본 Locale을 사용하여 WeekFields를 가져옵니다.
		// 여기서는 대한민국 기준으로 월요일이 한 주의 시작이라고 가정합니다.
		// 다른 Locale이 필요한 경우 Locale을 변경하세요.

		LocalDate thursday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.THURSDAY));
		WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);

		// 월의 주차 정보를 가져오기 위한 TemporalField
		TemporalField weekOfMonth = weekFields.weekOfMonth();

		// 월의 주차 계산
		int weekNumber = thursday.get(weekOfMonth);

		System.out.println(date + "는 " + thursday.getMonthValue() + "월의 " + weekNumber + "주차입니다.");
	}

}
