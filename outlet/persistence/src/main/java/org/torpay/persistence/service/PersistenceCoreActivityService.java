package org.torpay.persistence.service;

import java.util.List;

import org.torpay.persistence.model.Request;
import org.torpay.persistence.model.RequestParameter;

public interface PersistenceCoreActivityService {

	public void saveRequest(Request request) throws PersistenceException;

	public Request findRequestById(Long id) throws PersistenceException;

	public void saveRequestParameter(RequestParameter requestParameter)
			throws PersistenceException;

	public RequestParameter findRequestParameterById(Long id)
			throws PersistenceException;

	public RequestParameter findRequestParameterByName(String name)
			throws PersistenceException;

	public RequestParameter findRequestParameterByParent(String parent)
			throws PersistenceException;
	
	public List<RequestParameter> findRequestParameterByRequestId(Long requestId)
			throws PersistenceException;

}
