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
@Table(name="GCM_APP")
public class GcmApp {

	@Id
	@Column(length = 20, nullable = false)
	private String appCode;
	
	@Column(length = 64, nullable = false)
	private String appName;
	
	@Column(length = 256, nullable = false)
	private String regId;
	
	@Column(length = 32, nullable = false)
	private String apiKey;
	
	@Column(length = 1)
	private int retryCnt;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@OneToMany(mappedBy = "gcmAppInfo")
	private List<GcmSend> sendList = new ArrayList<GcmSend>();

	@Override
	public String toString() {
		return "GcmApp [appCode=" + appCode + ", appName=" + appName + ", regId=" + regId + ", apiKey=" + apiKey
				+ ", retryCnt=" + retryCnt + ", regDate=" + regDate + "]";
	}
	
	
	
}
