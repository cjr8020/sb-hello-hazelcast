package com.demo.configuration;

import com.demo.domain.Actor;
import com.demo.domain.serialization.ActorKryoSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.SerializerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hazelcast Configuration
 */
@Configuration
public class HazelcastConfiguration {

  public static final Logger logger = LoggerFactory.getLogger(HazelcastConfiguration.class);

  @Bean
  public Config hazelcastConfig(MapConfig mapConfig) {
    Config hazelcastConfig =  new Config("demo-hazelcast-instance");
    hazelcastConfig.setProperty("hazelcast.phone.home.enabled", "false");
    hazelcastConfig.addMapConfig(mapConfig);  // 20 seconds
    // actor serializer
    hazelcastConfig.getSerializationConfig().addSerializerConfig(actorSerializerConfig());

    return hazelcastConfig;
  }

  SerializerConfig actorSerializerConfig() {
    return new SerializerConfig()
        .setTypeClass(Actor.class)
        .setImplementation(new ActorKryoSerializer());
  }

}
