package org.wlfek.push.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.wlfek.push.domain.GcmInfo;
import org.wlfek.push.domain.GcmSend;
import org.wlfek.push.repository.GcmInfoRepository;
import org.wlfek.push.repository.GcmSendRepository;

@Component("myBean")
public class ScheduledTasks {
	
	@Autowired
	private GcmInfoRepository gcmInfoRepository;
	
	@Autowired
	private GcmSendRepository gcmSendRepository;
	
	private List<GcmInfo> gcmInfoList = new ArrayList<>();
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@PostConstruct
	public void initialize(){
		gcmInfoList = gcmInfoRepository.findAll();
	}
	
	/**
	 * gcm apiKey 나 registation_id 를 교체시 미리 메모리 올려 놓은 gcm 리스트 조회시 호출
	 * return 갱신된 정보
	 */
	public List<GcmInfo> renewalGcmInfo(){
		return gcmInfoList = gcmInfoRepository.findAll();
	}
	
	
	
	
	public void reportCurrentTime(EntityManager em){
		GcmSend gcmSend = new GcmSend();
		em.persist(gcmSend);
		
		
//		gcmSendRepository.findAll()
//		for(GcmInfo g: gcmInfoList){
//			System.out.println("The time is now " + dateFormat.format(new Date()));
//			System.out.println("Api key : "+ g.getApiKey());
//			System.out.println("App Code : "+ g.getAppCode());
//		}
	}
	
	public void reportCurrentTime2(){
		System.out.println("[Cron]The time is now " + dateFormat.format(new Date()));
	}
}
