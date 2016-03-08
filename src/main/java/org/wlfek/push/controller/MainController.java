package org.wlfek.push.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.wlfek.push.component.ScheduledTasks;
import org.wlfek.push.domain.GcmAppInfo;
import org.wlfek.push.domain.GcmDeviceInfo;

@Controller
@RequestMapping("/")
public class MainController {
	
	@Autowired
	//private MainService mainService;
	private ScheduledTasks scheduledTasks;
	
	@RequestMapping
	public @ResponseBody String index() {
		return "Hello Woniper Spring Boot~";
	}
	 
	@RequestMapping("/renewalGcmInfo")
	public @ResponseBody List<GcmAppInfo> getGcmInfo(){
		return scheduledTasks.renewalGcmInfo();
	}

}
