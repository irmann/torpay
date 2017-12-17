package org.torpay.persistence.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.torpay.persistence.model.RequestParameter;

public interface RequestParameterRepository {

	public void save(RequestParameter requestParameter)
			throws DataAccessException;

	public RequestParameter findById(Long id) throws DataAccessException;

	public RequestParameter findByName(String name) throws DataAccessException;

	public RequestParameter findByParent(String parent)
			throws DataAccessException;

	public List<RequestParameter> findByRequestId(Long requestId)
			throws DataAccessException;
}