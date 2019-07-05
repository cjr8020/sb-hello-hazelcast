package com.demo.service;

import com.demo.domain.Actor;
import com.demo.repository.ActorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@CacheConfig(cacheNames = "actors-cache")
public class ActorServiceImpl implements ActorService {

  private static final Logger logger = LoggerFactory.getLogger(ActorServiceImpl.class);

  private ActorRepository actorRepository;

  @Autowired
  public void setAcrotRepository(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  @Cacheable
  public Actor findById(Integer id) {
    if (logger.isInfoEnabled()) {
      logger.info("method `findById()` -> fetching data from DB");
    }
    return actorRepository.findById(id);
  }

  /**
   * Creates or updates an Actor record.
   * This method will also clear `allEntries` to ensure removal of stale data.
   * @param actor object contains new data
   */
  @CacheEvict(allEntries = true)
  public void saveActor(final Actor actor) {
    if (logger.isInfoEnabled()) {
      logger.info("executing `saveActor()`");
    }
    actorRepository.save(actor);
  }

  @Cacheable(key = "#root.methodName")
  public Iterable<Actor> readActors() {
    if (logger.isInfoEnabled()) {
      logger.info("method `readActors()` -> going to the DB to get DATA!");
    }
    return actorRepository.findAll();
  }

  @CacheEvict(allEntries = true)
  public void clearActorsCache() {
    // Intentionally blank
  }

}