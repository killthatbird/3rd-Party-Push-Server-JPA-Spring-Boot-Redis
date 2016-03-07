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
import org.wlfek.push.domain.GcmInfo;
import org.wlfek.push.domain.GcmSend;
import org.wlfek.push.repository.GcmInfoRepository;
import org.wlfek.push.repository.GcmSendRepository;

import com.google.android.gcm.server.Sender;

@Component("myBean")
public class ScheduledTasks {
	
	@Autowired
	private GcmInfoRepository gcmInfoRepository;
	
	@Autowired
	GcmSendRepository gcmSendRepository;
	
	private List<GcmInfo> gcmInfoList;
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
	public List<GcmInfo> renewalGcmInfo(){
		return gcmInfoList = gcmInfoRepository.findAll();
	}
	
	
	@Transactional
	public void checkPushMessage() throws DataAccessException {
		System.out.println("The time is now " + dateFormat.format(new Date()));
		List<GcmSend> result = getPushMessage();
		if(result.size() > 0) {
			// message queue에 넣기
			
			// 상태 업데이트
			updatePushStatus(3);
		}
	}
	
	public List<GcmSend> getPushMessage(){
		return gcmSendRepository.findByStatusCode(1);
	}
	
	public void updatePushStatus(int statusCode){
		getEntityManager().createQuery("update GcmSend set statusCode = :statusCode where statusCode = 1")
			.setParameter("statusCode", statusCode)
			.executeUpdate();
	}
	
	public void sendGcmPush(){
		
	}
}
