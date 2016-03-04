/**
 * wlfekymk@gmail.com
 * 
 */
package org.wlfek.push.service;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wlfek.push.domain.GcmInfo;
import org.wlfek.push.repository.GcmInfoRepository;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Sender;

@Service
public class MainService {
	
	private Sender sender;
	
//	@Autowired
//	private GcmInfoRepository gcmInfoRepository;
//	private List<GcmInfo> gcmInfoList = new ArrayList<>();
//	
//	@PostConstruct
//	public void initialize(){
//		gcmInfoList = gcmInfoRepository.findAll();
//		 
//	}
//	
	public GcmInfo getJpaInfo(){
		return null;
	}
	
	public void sendGcm(){
			
		String sendTitle = "";
		String sendMsg = "";
//		Sender sender = new Sender(APIKEY);
//		
//		try {
//			Message message = new Message.Builder()
//		            .addData("title", URLEncoder.encode(sendTitle, "UTF-8"))
//		            .addData("msg", URLEncoder.encode(sendMsg, "UTF-8"))
//		            .build();
//			sender.send(message, REGISTRATION_ID, RETRY_CNT);
//		
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
				
		
	}
	
	public void sendGcmMulti(){
		
	}

}
