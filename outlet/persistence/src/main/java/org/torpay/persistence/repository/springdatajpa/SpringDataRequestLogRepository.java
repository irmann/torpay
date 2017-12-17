package org.torpay.persistence.repository.springdatajpa;

import org.springframework.data.repository.Repository;
import org.torpay.persistence.model.RequestLog;
import org.torpay.persistence.repository.RequestLogRepository;

public interface SpringDataRequestLogRepository extends RequestLogRepository,
		Repository<RequestLog, Integer> {

}
