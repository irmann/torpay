package org.torpay.persistence.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.torpay.persistence.model.RequestLog;

public interface RequestLogRepository {

	void save(RequestLog requestLog) throws DataAccessException;

	Collection<RequestLog> findAll();

}