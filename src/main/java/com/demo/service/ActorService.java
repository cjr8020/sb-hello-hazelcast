package com.demo.service;

import com.demo.domain.Actor;

public interface ActorService {

  Iterable<Actor> listAllActors();

  Actor findById(Integer id);

  Actor saveActor(Actor actor);

}
