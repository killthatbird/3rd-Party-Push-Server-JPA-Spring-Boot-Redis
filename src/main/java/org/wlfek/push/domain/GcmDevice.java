package org.wlfek.push.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name="GCM_DEVICE")
public class GcmDevice {
	
	@Id
	@Column(length = 128)
	private String token;
	
	@Column(length = 32)
	private String userInfo;
	
	//@Enumerated(EnumType.STRING)
	private int regStatus;
	
	private int badge;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@OneToMany(mappedBy = "gcmDeviceInfo")
	private List<GcmSend> gcmSendInfos = new ArrayList<GcmSend>();
	
	public void addSendMessage(GcmSend gcmSendInfo){
		gcmSendInfos.add(gcmSendInfo);
		gcmSendInfo.setGcmDeviceInfo(this);
	}
	
}

enum PushStatus{
	SEND, WAIT, RETRY, FIN
}