package org.wlfek.push.domain;

import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name="GCM_SEND_INFO")
public class GcmSendInfo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String title;
	
	private String message;
	
	private int statusCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
	
	@ManyToOne
	@JoinColumn(name="APP_CODE")
	private GcmAppInfo gcmAppInfo; 
	
}
