package com.demo.configuration;

import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Cache configuration.
 */
@Configuration
public class CacheConfiguration {

  @Bean
  public MapConfig mapConfig() {
    return new MapConfig("default")
        .setMaxSizeConfig(new MaxSizeConfig(200, MaxSizeConfig.MaxSizePolicy.PER_NODE))
        .setEvictionPolicy(EvictionPolicy.LRU)
        .setTimeToLiveSeconds(20); // 20 sec
  }

}
