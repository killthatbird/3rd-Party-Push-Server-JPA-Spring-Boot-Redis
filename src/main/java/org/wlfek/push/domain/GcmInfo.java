package org.wlfek.push.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="GCM_INFO")
public class GcmInfo {

	@Id
	private String appCode;
	
	private String appName;
	
	private String regId;
	
	private String apiKey;
	
	private int retryCnt;
	
}
