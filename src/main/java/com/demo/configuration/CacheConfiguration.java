package com.demo.configuration;

import com.demo.domain.Actor;
import com.demo.domain.serialization.ActorKryoSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.InterfacesConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.MaxSizeConfig.MaxSizePolicy;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.SerializerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Hazelcast specific cache configuration.
 */
@Configuration
public class CacheConfiguration {

  /**
   * Hazelcast map configuration.
   * @return MapConfig
   */
  @Bean
  public MapConfig actorsMapConfig() {
    return new MapConfig("actors-cache")
        .setMaxSizeConfig(new MaxSizeConfig(100, MaxSizePolicy.PER_NODE))
        .setEvictionPolicy(EvictionPolicy.LFU)
        .setTimeToLiveSeconds(3600); // auth token lifespan is 1 hour
  }

  /**
   * Hazelcast config.
   * @return Config
   */
  @Bean
  public Config hazelcastConfig() {
    Config hazelcastConfig = new Config("hello  -hazelcast-instance");
    hazelcastConfig.setProperty("hazelcast.phone.home.enabled", "false");
    hazelcastConfig.addMapConfig(actorsMapConfig());  // tokens map

    // network config

    // disable bind to any interface
    hazelcastConfig.setProperty("hazelcast.socket.bind.any", "false");
    NetworkConfig networkConfig = hazelcastConfig.getNetworkConfig();
    InterfacesConfig interfacesConfig = networkConfig.getInterfaces();
    interfacesConfig.setEnabled(true)
        .addInterface("127.0.0.1");

    // join config
    JoinConfig joinConfig = networkConfig.getJoin();
    joinConfig.getMulticastConfig().setEnabled(false);
    joinConfig.getTcpIpConfig().setEnabled(false);
    return hazelcastConfig;
  }

  SerializerConfig actorSerializerConfig() {
    return new SerializerConfig()
        .setTypeClass(Actor.class)
        .setImplementation(new ActorKryoSerializer());
  }

}
