package std.demo.local.timing;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {

	public static void main(String[] args) throws ParseException {

		final SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date startDate = dateFormatter.parse("2017/07/27 17:12:00");

		Timer timer = new Timer();

		// timer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// System.out.println("start at " + LocalDateTime.now());
		// }
		// }, startDate);
		//
		// timer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// System.out.println("start at " + LocalDateTime.now());
		// }
		// }, 5000);

		// timer.schedule(new TimerTask() {
		// @Override
		// public void run() {
		// System.out.println("start at " + LocalDateTime.now());
		// }
		// }, startDate, 1000);

		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("start at " + LocalDateTime.now());

			}
		}, 5000, 1000);
		
		
		
	}
}
