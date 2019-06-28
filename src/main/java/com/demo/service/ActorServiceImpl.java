package com.demo.service;

import com.demo.domain.Actor;
import com.demo.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ActorServiceImpl implements ActorService {

  private ActorRepository actorRepository;

  @Autowired
  public void setAcrotRepository(ActorRepository actorRepository) {
    this.actorRepository = actorRepository;
  }

  public Actor findById(Integer id) {
    return actorRepository.findById(id);
  }

  public Actor saveActor(Actor actor) {
    return actorRepository.save(actor);
  }

  @Cacheable({"actors"})
  public Iterable<Actor> readActors() {
    return actorRepository.findAll();
  }

}