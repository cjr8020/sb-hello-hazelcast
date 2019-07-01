package com.demo.domain.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.InputChunked;
import com.esotericsoftware.kryo.io.OutputChunked;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Generic Kryo serializer for Hazelcast.
 */
public abstract class GenericHazelcastKryoSerializer<T> implements StreamSerializer<T> {

  private final int typeId;

  public GenericHazelcastKryoSerializer(int typeId) {
    this.typeId = typeId;
  }

  @Override
  public int getTypeId() {
    return typeId;
  }

  protected abstract Kryo getSharedThreadLocalKyro();

  @Override
  public void write(ObjectDataOutput objectDataOutput, T product) {
    Kryo kryo = getSharedThreadLocalKyro();
    OutputChunked output = new OutputChunked((OutputStream) objectDataOutput, 4096);
    kryo.writeClassAndObject(output, product);
    output.endChunks();
    output.flush();
  }

  @Override
  @SuppressWarnings("unchecked")
  public T read(ObjectDataInput objectDataInput) {
    InputStream in = (InputStream) objectDataInput;
    InputChunked inputChunked = new InputChunked(in, 4096);
    Kryo kryo = getSharedThreadLocalKyro();
    return (T) kryo.readClassAndObject(inputChunked);
  }

  @Override
  public void destroy() {
  }

}
