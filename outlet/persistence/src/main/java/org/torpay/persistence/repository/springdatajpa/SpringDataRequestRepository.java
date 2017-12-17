package org.torpay.persistence.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.torpay.persistence.model.Request;
import org.torpay.persistence.repository.RequestRepository;

public interface SpringDataRequestRepository extends RequestRepository,
		Repository<Request, Integer> {

}
