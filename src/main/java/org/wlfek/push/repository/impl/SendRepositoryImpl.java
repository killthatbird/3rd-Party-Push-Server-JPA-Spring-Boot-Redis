package org.wlfek.push.repository.impl;

import java.util.List;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.util.StringUtils;
import org.wlfek.push.domain.GcmSend;
import org.wlfek.push.domain.QGcmApp;
import org.wlfek.push.domain.QGcmDevice;
import org.wlfek.push.domain.QGcmSend;
import com.mysema.query.jpa.JPQLQuery;


public class SendRepositoryImpl extends QueryDslRepositorySupport implements CustomSendRepository{

	public SendRepositoryImpl() {
		super(GcmSend.class);		
	}

	@Override
	public List<GcmSend> sendList(int statusCode){
		QGcmSend gcmSend =  QGcmSend.gcmSend;
		QGcmApp gcmApp = QGcmApp.gcmApp;
		QGcmDevice gcmDevice = QGcmDevice.gcmDevice;
		
		JPQLQuery query = from(gcmSend);
		if(StringUtils.hasText(String.valueOf(statusCode))){
			query.join(gcmSend.gcmAppInfo, gcmApp)
			     .join(gcmSend.gcmDeviceInfo, gcmDevice)
			     .where(gcmSend.statusCode.eq(statusCode));
		}
		return query.list(gcmSend);
	}
}
