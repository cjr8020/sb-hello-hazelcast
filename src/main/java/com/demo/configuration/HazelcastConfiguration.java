package com.demo.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
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
  public Config hazelCastConfig(MapConfig mapConfig) {
    return new Config("demo-hazelcast-instance")
        .setProperty("hazelcast.phone.home.enabled", "false")
        .addMapConfig(mapConfig);  // 20 seconds
  }
}
