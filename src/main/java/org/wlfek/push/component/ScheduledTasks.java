/**
 * AUTHOR : Yang Min Kyu
 * DATE   : 2016. 3. 23.
 * TITLE  :  gcm task
 * MAIL   : wlfekymk@gmail.com
 *
**/
package org.wlfek.push.component;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;
import org.wlfek.push.domain.GcmApp;
import org.wlfek.push.domain.GcmSend;

import org.wlfek.push.repository.AppRepository;
import org.wlfek.push.repository.SendRepository;
import org.wlfek.push.repository.impl.CustomSendRepository;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

@Component("myBean")
public class ScheduledTasks {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AppRepository gcmInfoRepository;
	
	@Autowired
	private SendRepository gcmSendRepository;

	@Autowired
	private CustomSendRepository customSendRepository;
	
	private Queue<GcmSend> gcmSendQueue;
	
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
	
	@PostConstruct
	public void initialize(){
		gcmInfoList = gcmInfoRepository.findAll();
		gcmSendQueue = new LinkedList<GcmSend>();
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

		// 보낼 push data 조회 status가 1인 것만
		List<GcmSend> gcmSendList = customSendRepository.sendList(1);
		// 조회할 내용이 있으면
		if(gcmSendList.size() > 0) {
			logger.info("Gcm List >>>> ", gcmSendList);
			//push data 읽어서 queue 적재
			//redis 적재
			for(GcmSend gcmSend : gcmSendList){
				pushSendQueueAdd(gcmSend);
				logger.info("queue에 적재후 status 3 업데이트");;
				gcmSend.setStatusCode(3);
				gcmSendRepository.save(gcmSend);
			}
			gcmSendRepository.flush();
		}
	}
	
	public void pushSendQueueAdd(GcmSend gcmSend){
		synchronized (gcmSendQueue) {
			gcmSendQueue.offer(gcmSend);
		}
	}
	
	public void setSender(GcmSend gcmSend){
		sender = new Sender(gcmSend.getGcmAppInfo().getApiKey());
	}

	public void sendGcmPushMulti(List<GcmSend> gcmSendList){
		Message message = null;
		List<String> tokenList = new ArrayList<String>();
		if(gcmSendList != null && gcmSendList.size() > 0){
			for(GcmSend gs: gcmSendList){
				message = new Message.Builder()
									 .addData("Message", gs.getMessage())
									 .addData("Title", gs.getTitle())									 
									 .build();
				tokenList.add(gs.getGcmDeviceInfo().getRegId());
			}
		}
		
		try {
			if (tokenList.size() > 0) {
				MulticastResult multicastResult = sender.send(message, tokenList, 5);	
				if (multicastResult != null && multicastResult.getTotal() > 0 ) {
	
				}
			}
		} catch (Exception e) {
			logger.error("Error message : ", e.getMessage());
		}
	}

}
