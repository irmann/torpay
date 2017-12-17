package org.torpay.persistence.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.torpay.common.util.ErrorCodes;
import org.torpay.persistence.model.RequestLog;
import org.torpay.persistence.repository.RequestLogRepository;

@Service("persistenceEntranceService")
public class PersistenceEntranceServiceImpl implements
		PersistenceEntranceService {

	private RequestLogRepository requestLogRepository;

	@Autowired
	public PersistenceEntranceServiceImpl(
			RequestLogRepository requestLogRepository) {
		this.requestLogRepository = requestLogRepository;
	}

	@Override
	public void saveRequestLog(RequestLog requestLog)
			throws PersistenceException {
		try {
			requestLogRepository.save(requestLog);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not save ReuestLog", "PersistenceEntranceService", de);
		}

	}

	@Override
	public Collection<RequestLog> findRequestLogs() throws PersistenceException {
		try {
			return requestLogRepository.findAll();
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not perform findAll on ReuestLog table",
					"PersistenceEntranceService", de);
		}
	}
}
