# Elastic Shakesspeare

This is a very simple project based on Spring Data Elasticsearch, Spring MVC and Thymeleaf.

The used test data is taken from elastic.com's Kibana examples ([https://www.elastic.co/guide/en/kibana/3.0/snippets/shakespeare.json](https://www.elastic.co/guide/en/kibana/3.0/snippets/shakespeare.json)). It has to be modified before importing into elasticsearch so that the field names don't contain underscores anymore (e.g. from line_number to linenumber etc.).

This project's intention is to complete my Docker Elasticsearch example project which can be found at  (which uses Elasticsearch 1.7.4, see below).

# Requirements

Currently, this version is based on Spring Boot Starter 1.3.4 and therefore using Spring Data Elasticsearch which requires Elasticsearch 1.x. 
You can simply upgrade (in that case you've to change minor stuff in the configuration class).

# License

Apache License, Version 2.0
