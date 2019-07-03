package com.demo.service;

import com.demo.domain.Actor;
import com.demo.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {

  private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

  private ActorRepository actorRepository;

  @Autowired
  public void setAcrotRepository(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor findById(Integer id) {
    return actorRepository.findById(id);
  }

  @CacheEvict(value = "actors", allEntries = true)
  public void saveActor(final Actor actor) {
    actorRepository.save(actor);
  }

  @Cacheable({"actors"})
  public Iterable<Actor> readActors() {
    if (logger.isInfoEnabled()) {
      logger.info("method `readActors()` -> going to the DB to get DATA!");
    }
    return actorRepository.findAll();
  }

}