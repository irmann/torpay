package org.torpay.persistence.repository;

import org.springframework.dao.DataAccessException;
import org.torpay.persistence.model.Request;

public interface RequestRepository {

	void save(Request request) throws DataAccessException;

	Request findById(Long id) throws DataAccessException;
}