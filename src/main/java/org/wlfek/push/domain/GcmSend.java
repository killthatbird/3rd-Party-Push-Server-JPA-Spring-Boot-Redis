package org.wlfek.push.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name="GCM_SEND")
public class GcmSend {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "APP_CODE")
	private GcmApp gcmAppInfo; 
	
	@ManyToOne
	@JoinColumn(name = "REG_ID")
	private GcmDevice gcmDeviceInfo;
	
	private String title;
	
	@Column(length = 1024)
	private String message;
	
	@Column
	private Long groupId;
	
	//@Enumerated(EnumType.STRING)
	private int statusCode;

	@Temporal(TemporalType.TIMESTAMP)
	private Date regDate;
}
