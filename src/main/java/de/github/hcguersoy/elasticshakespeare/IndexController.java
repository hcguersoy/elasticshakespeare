/**
 * Copyright © 2016 H.-C.Guersoy (hcguersoy@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.github.hcguersoy.elasticshakespeare;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;

@Controller
@Slf4j
public class IndexController {

    @Resource
    ElasticshakespeareRepository repository;

    final static String DEFAULT_QUERY = "To be or not to be";

    @RequestMapping(value = "/", method = RequestMethod.GET)
    String index(Model model) {
        log.debug("Setting searchQuery to default value and searching");
        SearchQuery result = new SearchQuery(DEFAULT_QUERY);
        result.setCitations(repository.findByTextentry(result.getSearchQuery()));
        log.debug("Result count is:" + result.getCitations().size());
        model.addAttribute("searchQuery", result);
        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    String index(@ModelAttribute SearchQuery searchQuery, Model model) {
        log.debug("Search query is [" + searchQuery.getSearchQuery() + "]");
        searchQuery.setCitations(repository.findByTextentry(searchQuery.getSearchQuery()));
        log.debug("Result count is:" + searchQuery.getCitations().size());
        model.addAttribute("searchQuery", searchQuery);
        return "index";
    }

}
