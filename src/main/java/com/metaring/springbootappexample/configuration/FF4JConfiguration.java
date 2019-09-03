package com.metaring.springbootappexample.configuration;

import java.util.HashMap;
import java.util.Map;

import org.ff4j.FF4j;
import org.ff4j.cache.InMemoryCacheManager;
import org.ff4j.mongo.store.EventRepositoryMongo;
import org.ff4j.mongo.store.FeatureStoreMongo;
import org.ff4j.mongo.store.PropertyStoreMongo;
import org.ff4j.property.Property;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
public class FF4JConfiguration {

    public static final Map<String, Property<?>> GLOBAL_PROPERTIES = new HashMap<>();

    @Value("${spring.data.mongodb.host:localhost}")
    private String databaseConnectionHost;

    @Value("${spring.data.mongodb.port:27017}")
    private Integer databaseConnectionPort;

    @Bean
    public FF4j getFF4j() {
        FF4j ff4j = new FF4j("ff4j-features.xml").autoCreate(true);
        GLOBAL_PROPERTIES.putAll(ff4j.getProperties());
        // Define Connectivity to DB (see with authorization - Using Mongo Driver
        MongoClient mongoClient = new MongoClient(databaseConnectionHost, databaseConnectionPort);
        // Using Spring-data-mongodb
        MongoDatabase mongoDatabase = mongoClient.getDatabase("ff4j");
        ff4j.setFeatureStore(new FeatureStoreMongo(mongoDatabase.getCollection("ff4j_features"), "ff4j-features.xml"));
        ff4j.setPropertiesStore(new PropertyStoreMongo(mongoDatabase.getCollection("ff4j_properties"), "ff4j-features.xml"));
        // Enable audit
        ff4j.setEventRepository(new EventRepositoryMongo(mongoClient, "ff4j"));
        ff4j.audit(true);
        // Enable Cache Proxy
        ff4j.cache(new InMemoryCacheManager());
        return ff4j;
    }
}