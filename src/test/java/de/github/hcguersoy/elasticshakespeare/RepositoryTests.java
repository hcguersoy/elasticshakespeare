/**
 * Copyright © 2016 H.-C.Guersoy (hcguersoy@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.github.hcguersoy.elasticshakespeare;


import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(ElasticShakespeareConfig.class)
public class RepositoryTests {

    @Resource
    private ElasticshakespeareRepository repository;

    @Test
    public void shouldFind100() {
        log.debug("**** Searching for first 100 citations");
        Iterable<Citation> citations = repository.findAll(new PageRequest(0, 100));
        log.debug("...done. Now iterating");
        for (Citation citation : citations) {
            log.debug(citation.toString());
        }
        log.debug("**** Finished.");
    }

    @Test
    public void shouldFindHamlet() {
        log.debug("Searching for hamlet...");
        List<Citation> citations = repository.findByTextentry("To be or not to be");
        log.debug("Found citations:" + citations.size());
        for (Citation citate : citations) {
            log.debug("To be or not to be?:" + citate);
        }
        assertTrue(citations.size() > 0);
    }
}
