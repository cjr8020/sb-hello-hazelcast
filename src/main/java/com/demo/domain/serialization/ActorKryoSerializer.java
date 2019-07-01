package com.demo.domain.serialization;

import com.demo.domain.Actor;
import com.esotericsoftware.kryo.Kryo;

/**
 * Kryo serializer for Actor class.
 */
public class ActorKryoSerializer extends GenericHazelcastKryoSerializer<Actor> {

  private static final ThreadLocal<Kryo> kryoThreadLocal
      = ThreadLocal.withInitial(() -> {
    Kryo kryo = new Kryo();
    kryo.register(Actor.class);
    return kryo;
  });

  public ActorKryoSerializer() {
    super(SerializationId.ID);
  }

  @Override
  protected Kryo getSharedThreadLocalKyro() {
    return kryoThreadLocal.get();
  }
}
