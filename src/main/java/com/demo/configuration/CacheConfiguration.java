package com.demo.configuration;

import com.demo.domain.Actor;
import com.demo.domain.serialization.ActorKryoSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.SerializerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hazelcast specific cache configuration.
 */
@Configuration
public class CacheConfiguration {

  @Bean
  public MapConfig mapConfig() {
    return new MapConfig("default")
        .setMaxSizeConfig(new MaxSizeConfig(300, MaxSizeConfig.MaxSizePolicy.PER_NODE))
        .setEvictionPolicy(EvictionPolicy.LRU)
        .setTimeToLiveSeconds(20); // 20 sec
  }

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
