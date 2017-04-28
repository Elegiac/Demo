package std.demo.jdk8;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NewApi {

	public static void main(String[] args) {
		//
		LocalDate date = LocalDate.now();
		LocalDate date2 = LocalDate.parse("2010-01-10");
		LocalDate date3 = LocalDate.now();
		
		
		LocalTime time = LocalTime.now();
		LocalTime time2 = LocalTime.parse("10:10:10");
		LocalTime time3 = LocalTime.of(10, 10, 10);
		
		LocalDateTime dateTime = LocalDateTime.now();
		LocalDateTime dateTime2 = LocalDateTime.now();
		LocalDateTime dateTime3 = LocalDateTime.now();
		
	}

}
