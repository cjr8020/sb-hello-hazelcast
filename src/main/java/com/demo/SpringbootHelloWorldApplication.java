package com.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootHelloWorldApplication {

  private static final Logger log = LoggerFactory.getLogger(SpringbootHelloWorldApplication.class);

  public static void main(String[] args) {
    log.info("SpringbootHelloWorldApplication::::Started Application");
    SpringApplication.run(SpringbootHelloWorldApplication.class, args);
  }
 
}
