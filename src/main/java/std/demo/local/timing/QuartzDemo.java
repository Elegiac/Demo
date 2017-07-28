package std.demo.local.timing;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzDemo {

	public static void main(String[] args) throws SchedulerException, InterruptedException {

		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

		JobDetail job = JobBuilder.newJob(MyJob.class).withIdentity("job1", "group1").build();

		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger1", "CronTriggerGroup")
				.withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * * ?")).startNow().build();

		scheduler.scheduleJob(job, trigger);

		scheduler.start();

		Thread.sleep(10000);

		scheduler.shutdown();

		// 创建新trigger
		// Trigger newTrigger = TriggerBuilder.newTrigger().withIdentity("CronTrigger2",
		// "CronTriggerGroup")
		// .withSchedule(CronScheduleBuilder.cronSchedule("*/1 * * * *
		// ?")).startNow().build();
		// 重置任务
		// scheduler.rescheduleJob(trigger.getKey(), newTrigger);

	}
}
