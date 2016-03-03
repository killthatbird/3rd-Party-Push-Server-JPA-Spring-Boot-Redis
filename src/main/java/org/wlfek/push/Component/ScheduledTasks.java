package org.wlfek.push.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component("myBean")
public class ScheduledTasks {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	public void reportCurrentTime(){
		System.out.println("[fixedRate]The time is now " + dateFormat.format(new Date()));
		
	}
	
	public void reportCurrentTime2(){
		System.out.println("[Cron]The time is now " + dateFormat.format(new Date()));
	}
}
