package com.demo.repository;

import com.demo.domain.Actor;
import org.springframework.data.repository.CrudRepository;

/**
 * Actor repository.
 */
public interface ActorRepository extends CrudRepository<Actor, String> {

  Actor findById(Integer id);
  
}
