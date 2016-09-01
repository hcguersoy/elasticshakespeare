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

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.net.InetAddress;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.github.hcguersoy.elasticshakespeare")
@ComponentScan
@EnableDiscoveryClient
@Slf4j
public class ElasticShakespeareConfig {

    @Autowired
    public DiscoveryClient consulClient;

    public List<URI> getElasticURIs() {
        log.debug("description" + consulClient.description());
        log.debug("servies: " + consulClient.getServices());
        List<ServiceInstance> list = consulClient.getInstances("elastic");
        List<URI> elasticUris = new ArrayList<URI>();
        if (list != null && list.size() > 0 ) {
            for (ServiceInstance serviceInstance : list) {
                elasticUris.add(serviceInstance.getUri());
            }
        }
        return elasticUris;
    }

    @Bean
    @SneakyThrows
    public Client client() {
        List<URI> elasticURIs = getElasticURIs();
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true).build();

        TransportClient client = new TransportClient(settings);
        log.debug("Created es client");
        for (URI elasticURI : elasticURIs) {
            String elasticHost = elasticURI.getHost();
            int elasticPort = elasticURI.getPort();
            TransportAddress address = new InetSocketTransportAddress(elasticHost, elasticPort);
            client.addTransportAddress(address);
            log.info("Added [" + elasticURI.toString() + "] seed transport address");
        }
        log.debug("configured transport adresses: " + client.transportAddresses());
        return client;
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchTemplate(client());
    }

}
