package org.torpay.persistence.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.model.RequestParameter;
import org.torpay.persistence.repository.RequestParameterRepository;

public interface SpringDataRequestParameterRepository extends RequestParameterRepository,
		Repository<RequestParameter, Integer> {

}
