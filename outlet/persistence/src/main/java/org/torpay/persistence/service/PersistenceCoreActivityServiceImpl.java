package org.torpay.persistence.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.torpay.common.util.ErrorCodes;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.repository.RequestParameterRepository;
import org.torpay.persistence.repository.RequestRepository;

@Service("persistenceCoreActivityService")
public class PersistenceCoreActivityServiceImpl implements
		PersistenceCoreActivityService {

	private RequestRepository requestRepository;
	private RequestParameterRepository requestParameterRepository;

	@Autowired
	public PersistenceCoreActivityServiceImpl(
			RequestRepository requestRepository,
			RequestParameterRepository requestParameterRepository) {
		this.requestRepository = requestRepository;
		this.requestParameterRepository = requestParameterRepository;
	}

	@Override
	@Transactional
	public void saveRequest(Request request) throws PersistenceException {
		try {
			requestRepository.save(request);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not save reuest:" + request.toString(),
					"PersistenceCoreActivityService", de);
		}

	}

	@Override
	public Request findRequestById(Long id) throws PersistenceException {
		try {
			return requestRepository.findById(id);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not peform findById on reuest with id=" + id,
					"PersistenceCoreActivityService", de);
		}
	}

	@Override
	@Transactional
	public void saveRequestParameter(RequestParameter requestParameter)
			throws PersistenceException {
		try {
			requestParameterRepository.save(requestParameter);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not save reuest parameter:"
							+ requestParameter.toString(),
					"PersistenceCoreActivityService", de);
		}

	}

	@Override
	public RequestParameter findRequestParameterById(Long id)
			throws PersistenceException {
		try {
			return requestParameterRepository.findById(id);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not peform findById on ReuesParameter with id=" + id,
					"PersistenceCoreActivityService", de);
		}
	}

	@Override
	public RequestParameter findRequestParameterByName(String name)
			throws PersistenceException {
		try {
			return requestParameterRepository.findByName(name);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not peform findByName on ReuesParameter with name="
							+ name, "PersistenceCoreActivityService", de);
		}
	}

	@Override
	public RequestParameter findRequestParameterByParent(String parent)
			throws PersistenceException {
		try {
			return requestParameterRepository.findByParent(parent);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not peform findByParent on ReuesParameter with parent="
							+ parent, "PersistenceCoreActivityService", de);
		}
	}

	@Override
	public List<RequestParameter> findRequestParameterByRequestId(Long requestId)
			throws PersistenceException {
		try {
			return requestParameterRepository.findByRequestId(requestId);
		} catch (DataAccessException de) {
			throw new PersistenceException(ErrorCodes.TECHNICAL_ERROR,
					"can not peform findByRequestId on ReuesParameter with requestId="
							+ requestId, "PersistenceCoreActivityService", de);
		}
	}

}
