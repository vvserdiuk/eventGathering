package com.github.vvserdiuk.repository;

import com.github.vvserdiuk.EventGatheringApplication;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by vvserdiuk on 07.04.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(EventGatheringApplication.class)
public class EventRepositoryTest {

    Logger LOG = Logger.getLogger(EventRepositoryTest.class);

    @Autowired
    EventRepository repository;

    @Test
    public void testGetAll() throws Exception {
        LOG.info(repository.findAll());
    }

    @Test
    public void testGetAllByCommunityId() throws Exception {
        LOG.info(repository.findByCommunityId(1));
    }

    @Test
    public void testFindByTitleContaining() throws Exception {
        LOG.info("!!!" + repository.findByTitleContaining("мастер"));
    }
}
