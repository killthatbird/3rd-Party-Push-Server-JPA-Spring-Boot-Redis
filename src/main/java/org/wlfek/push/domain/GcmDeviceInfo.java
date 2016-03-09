package org.wlfek.push.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name="GCM_DEVICE_INFO")
public class GcmDeviceInfo {
	
	@Id
	@Column(length = 256)
	private String token;
	
	@Column(length = 32)
	private String userInfo;
	
	@Column(length = 1)
	private String regStatus;
	
	private int badge;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate; 
	
}
