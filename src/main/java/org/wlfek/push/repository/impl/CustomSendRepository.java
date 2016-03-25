package org.wlfek.push.repository.impl;

import java.util.List;

import org.wlfek.push.domain.GcmSend;

public interface CustomSendRepository {

	public List<GcmSend> sendList(int statusCode);
}
