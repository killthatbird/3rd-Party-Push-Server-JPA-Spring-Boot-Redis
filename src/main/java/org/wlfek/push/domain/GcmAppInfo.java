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
	private String appCode;
	
	private String appName;
	
	private String regId;
	
	private String apiKey;
	
	private int retryCnt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@OneToMany(mappedBy = "gcmAppInfo")
	List<GcmSendInfo> sendList = new ArrayList<GcmSendInfo>();
	
}
