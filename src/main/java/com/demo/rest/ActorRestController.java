package com.demo.rest;

import com.demo.domain.Actor;
import com.demo.service.ActorService;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActorRestController {

  private static final Logger logger
      = LoggerFactory.getLogger(ActorRestController.class);

  @Autowired private ActorService actorService;

  @RequestMapping(
      value = "/actor",
      method = RequestMethod.POST,
      consumes = "application/json; charset=UTF-8"
  )
  public ResponseEntity<Object> createActor(@Valid @RequestBody final Actor actor) {
    final StopWatch sw = new StopWatch();
    ResponseEntity<Object> responseEntity;

    try {
      sw.start();
      actorService.saveActor(actor);
      responseEntity = new ResponseEntity<>(
          actor,
          new HttpHeaders(),
          HttpStatus.CREATED);

    } finally {
      sw.stop();
      if (logger.isInfoEnabled()) {
        logger.info("`createActor completed in {}ms", sw.getTotalTimeMillis());
      }
    }

    return responseEntity;
  }

  /**
   * Provides a list of actors.
   *
   * @return Iterable of Actor
   */
  @RequestMapping(
      value = "/actors",
      method = RequestMethod.GET)
  public Iterable<Actor> listActors() {

    Iterable<Actor> listOfActors;
    StopWatch stopWatch = new StopWatch();

    if (logger.isInfoEnabled()) {
      logger.info("processing 'listActors' request");
    }

    try {
      stopWatch.start();
      listOfActors = actorService.readActors();
    } finally {
      stopWatch.stop();
      if (logger.isInfoEnabled()) {
        logger.info("'listActors` completed in {}ms", stopWatch.getTotalTimeMillis());
      }
    }

    return listOfActors;
  }


}
