/**
 * AUTHOR : Yang Min Kyu
 * DATE   : 2016. 3. 23.
 * TITLE  :  gcm task
 * MAIL   : wlfekymk@gmail.com
 *
**/
package org.wlfek.push.component;

import java.util.ArrayList;

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
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.wlfek.push.domain.GcmApp;
import org.wlfek.push.domain.GcmSend;

import org.wlfek.push.repository.AppRepository;
import org.wlfek.push.repository.SendRepository;
import org.wlfek.push.repository.impl.CustomSendRepository;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;

@Component("myBean")
public class ScheduledTasks {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AppRepository gcmAppRepository;
	
	@Autowired
	private SendRepository gcmSendRepository;

	@Autowired
	private CustomSendRepository customSendRepository;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;
	
	private Queue<GcmSend> gcmSendQueue; // 단건용 message queue
	private Queue<List<GcmSend>> listGcmSendQueue; // 여러건 발송용 message queue
	
	private List<GcmApp> gcmAppList;
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
		gcmAppList = gcmAppRepository.findAll();
		logger.info("App List Info Load", gcmAppList.toString());
		gcmSendQueue = new LinkedList<GcmSend>();
		listGcmSendQueue = new LinkedList<List<GcmSend>>();
	}
		
	
	/**
	 * gcm apiKey 나 registation_id 를 교체시 미리 메모리 올려 놓은 gcm 리스트 조회시 호출
	 * return 갱신된 정보
	 */
	public List<GcmApp> renewalGcmInfo(){
		return gcmAppList = gcmAppRepository.findAll();
	}
	
	@Transactional
	public void gcmSingleSender(){
		synchronized (gcmSendQueue) {
			System.out.println("SingleSender Size : " + gcmSendQueue.size());
			GcmSend singleGcmSend = null;
			while ((singleGcmSend = gcmSendQueue.poll()) != null) {
				setSender(singleGcmSend);
				logger.info(singleGcmSend.getGcmDeviceInfo().getRegId());
				sendGcmPushSingle(singleGcmSend);
				
			} 
		}
	}
	
	@Transactional
	public void gcmMultiSender(){
		synchronized (listGcmSendQueue) {
			System.out.println("MultiSender Size : " + listGcmSendQueue.size());
			List<GcmSend> multiGcmSend = null;
			while ((multiGcmSend = listGcmSendQueue.poll()) != null) {
				setSender(multiGcmSend.get(0));
				sendGcmPushMulti(multiGcmSend);
			} 
		}
	}
	
	@Transactional
	public void checkPushMessageAndAddQueue() throws DataAccessException {
		// 보낼 push data 조회 status가 1인 것만
		List<GcmSend> gcmSendList = customSendRepository.sendList(1);
		// 조회할 내용이 있으면
		if(gcmSendList.size() > 0) {
			logger.info("Gcm List >>>> ", gcmSendList);
			//push data 읽어서 queue 적재
			//차후 redis에 적재할 예정
		//	pushSendQueueListAdd(gcmSendList);
		//	redisTemplate.opsForValue().multiSet(gcmSendList);
		//jedis.rpush(key, strings)
		//ListOperations<String, GcmSend> list;
		//	redisTemplate.					
			List<GcmSend> gcmMultiSendList = new ArrayList<>();
			long beforeGroupId = 0;
			
			logger.info("DB 조회 했을때 총 건수 : "+ gcmSendList.size());
			
			for(GcmSend gcmSend : gcmSendList){
				if(gcmSend.getGroupId()!=null){
					logger.debug("groupId : "+ gcmSend.getGroupId());
					if(beforeGroupId != gcmSend.getGroupId()){
						logger.debug("gcmMultiSendList.size()  : " + gcmMultiSendList.size() );
						if(gcmMultiSendList.size() > 0){
							pushSendQueueListAdd(gcmMultiSendList);
						}
						//초기화
						gcmMultiSendList = new ArrayList<>();
						beforeGroupId = gcmSend.getGroupId();
					} 
					gcmMultiSendList.add(gcmSend);
				} else {
					pushSendQueueAdd(gcmSend);
					
				}
				
				gcmSend.setStatusCode(3);
				gcmSendRepository.save(gcmSend);
			}
			
			if(gcmMultiSendList.size() > 0){
				pushSendQueueListAdd(gcmMultiSendList);
			}
			
			logger.info("단건 큐 사이즈 : "+ gcmSendQueue.size());
			logger.info("리스트형 건 큐 사이즈 : "+ listGcmSendQueue.size());
			gcmSendRepository.flush();
		}
		
	}
	
	public void pushSendQueueListAdd(List<GcmSend> listGcmSend){
		List<String> stringList = new ArrayList<>();
		listGcmSend.stream().forEach(gcmSend -> stringList.add(gcmSend.toString()));
		redisTemplate.opsForList().rightPushAll("listSendQueue", stringList);
		synchronized (listGcmSendQueue) {
			listGcmSendQueue.offer(listGcmSend);
		}
	}
	
	public void pushSendQueueAdd(GcmSend gcmSend){
		redisTemplate.opsForList().rightPush("sendQueue", gcmSend.toString());
		synchronized (gcmSendQueue) {
			gcmSendQueue.offer(gcmSend);
		}
	}
	
	public void setSender(GcmSend gcmSend){
		sender = new Sender(gcmSend.getGcmAppInfo().getApiKey());
	}
	
	public Result sendGcmPushSingle(GcmSend gcmSend){
		Result sendReuslt = null;
		Message message = null;
		message = new Message.Builder()
				 .addData("Message", gcmSend.getMessage())
				 .addData("Title", gcmSend.getTitle())									 
				 .build();
		try {
			sendReuslt = sender.send(message, gcmSend.getGcmDeviceInfo().getRegId(), 5);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sendReuslt;
	}

	public void sendGcmPushMulti(List<GcmSend> gcmSendList){
		Message message = null;
		List<String> regIdList = new ArrayList<String>();
		if(gcmSendList != null && gcmSendList.size() > 0){
			for(GcmSend gcmSend: gcmSendList){
				message = new Message.Builder()
									 .addData("Message", gcmSend.getMessage())
									 .addData("Title", gcmSend.getTitle())									 
									 .build();
				regIdList.add(gcmSend.getGcmDeviceInfo().getRegId());
			}
		}
		
		try {
			if (regIdList.size() > 0) {
				MulticastResult multicastResult = sender.send(message, regIdList, 5);	
				if (multicastResult != null && multicastResult.getTotal() > 0 ) {
					
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
