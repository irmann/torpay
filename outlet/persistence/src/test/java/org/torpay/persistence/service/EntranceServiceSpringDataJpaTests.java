
package org.torpay.persistence.service;

import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@ContextConfiguration(locations = {"classpath:spring/persistence-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("spring-data-jpa")
public class EntranceServiceSpringDataJpaTests extends AbstractEntranceServiceTests {

}