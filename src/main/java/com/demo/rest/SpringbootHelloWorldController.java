package com.demo.rest;

import com.demo.domain.Actor;
import com.demo.service.ActorService;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpringbootHelloWorldController {

  private static final Logger logger
      = LoggerFactory.getLogger(SpringbootHelloWorldController.class);

  @Autowired private ActorService actorService;

  /**
   * Provides a list of actors.
   *
   * @return Iterable of Actor
   */
  @RequestMapping(value = "/listActors", method = RequestMethod.GET)
  public Iterable<Actor> listActors() {

    Iterable<Actor> listOfActors = () -> new ArrayList<Actor>().iterator();
    StopWatch stopWatch = new StopWatch();

    if (logger.isInfoEnabled()) {
      logger.info("processing 'listActors' request");
    }

    try {
      stopWatch.start();
      listOfActors = actorService.listAllActors();
    } catch (Throwable throwable) {
      logger.error("Encountered unexpected exception", throwable.getMessage());
    } finally {
      stopWatch.stop();
      if (logger.isInfoEnabled()) {
        logger.info("'listActors` completed in {}ms", stopWatch.getTotalTimeMillis());
      }
    }

    return listOfActors;
  }


}
