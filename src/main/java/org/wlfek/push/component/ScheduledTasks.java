package org.wlfek.push.component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.wlfek.push.domain.GcmApp;
import org.wlfek.push.domain.GcmDevice;
import org.wlfek.push.domain.GcmSend;

import org.wlfek.push.repository.GcmAppRepository;
import org.wlfek.push.repository.GcmSendRepository;

import com.google.android.gcm.server.Sender;

@Component("myBean")
public class ScheduledTasks {

	@Autowired
	private GcmAppRepository gcmInfoRepository;
	
	@Autowired
	GcmSendRepository gcmSendRepository;
	
	private List<GcmApp> gcmInfoList;
	private Sender sender;
	
	protected EntityManager entityManager;
	 
	public EntityManager getEntityManager() {
		return entityManager;
	}
	
	@PersistenceContext
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	
	@PostConstruct
	public void initialize(){
		//sender = new Sender();
		gcmInfoList = gcmInfoRepository.findAll();
	}
	
	/**
	 * gcm apiKey 나 registation_id 를 교체시 미리 메모리 올려 놓은 gcm 리스트 조회시 호출
	 * return 갱신된 정보
	 */
	public List<GcmApp> renewalGcmInfo(){
		return gcmInfoList = gcmInfoRepository.findAll();
	}
	
	
	@Transactional
	public void checkPushMessage() throws DataAccessException {
		System.out.println("The time is now " + dateFormat.format(new Date()));
		List<GcmSend> result = gcmSendRepository.findByStatusCode(1);
		if(result.size() > 0) {
			//push data 읽어서 queue 적재
			//redis 적재
			for(GcmSend gsi : result){
				gsi.setStatusCode(3);
				gcmSendRepository.save(gsi);
			}
			gcmSendRepository.flush();
		}
	}
	
	public void sendGcmPush(){
		
	}
}
