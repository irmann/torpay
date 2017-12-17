package org.torpay.persistence.service;

import java.util.Collection;

import org.torpay.persistence.model.RequestLog;

public interface PersistenceEntranceService {

	public void saveRequestLog(RequestLog requestLog)
			throws PersistenceException;

	public Collection<RequestLog> findRequestLogs() throws PersistenceException;

}
