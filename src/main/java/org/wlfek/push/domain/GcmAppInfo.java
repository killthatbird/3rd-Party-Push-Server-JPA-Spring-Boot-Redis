package org.wlfek.push.domain;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Data
@Entity
@Table(name="GCM_APP_INFO")
public class GcmAppInfo {

	@Id
	@Column(length = 20)
	private String appCode;
	
	@Column(length = 64)
	private String appName;
	
	@Column(length = 256)
	private String regId;
	
	@Column(length = 32)
	private String apiKey;
	
	@Column(length = 1)
	private int retryCnt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@OneToMany(mappedBy = "gcmAppInfo")
	List<GcmSendInfo> sendList = new ArrayList<GcmSendInfo>();
	
}
