package com.demo.service;

import com.demo.domain.Actor;

public interface ActorService {

  Iterable<Actor> readActors();

  Actor findById(Integer id);

  void saveActor(Actor actor);

}
