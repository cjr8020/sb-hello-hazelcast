# sb-hello-hazelcast

## Hazelcast debug and diagnostics

To enable add `-Dhazelcast.diagnostics.enabled=true` to the JVM arguments.


## Serialization options

* DataSerializable
* Portable
* Custom (Kryo `StreamSerializer` or Jackson Smile `ByteArraySerializer`)


### Custom Serialization

1. facilitates separation of concerns,
   i.e. the serialization code is separate from the class itself.

2. because it facilitates separation of concerns, there’s no required dependency
   on Hazelcast in the actual classes being serialized.
   This is particularly important if you don’t own or control the classes,
   and thus cannot implement any of the Hazelcast serialization interfaces.

3. Full constructor freedom.
   Because the instantiation is done in the Serializer class,
   which you write and control, there’s full freedom in how the class is designed,
   e.g. no need for a default constructor.

#### Kryo serialization

**`GenericHazelCastKryoSerializer.java`**

```
public abstract class GenericHazelcastKryoSerializer<T> implements StreamSerializer<T> {

  private final int typeId;

  public GenericHazelcastKryoSerializer(int typeId) {
    this.typeId = typeId;
  }

  @Override
  public int getTypeId() {
    return typeId;
  }

  @Override
  public void write(ObjectDataOutput objectDataOutput, T product)
      throws IOException {
    Kryo kryo = getSharedThreadLocalKyro();
    OutputChunked output = new OutputChunked((OutputStream) objectDataOutput, 4096);
    kryo.writeClassAndObject(output, product);
    output.endChunks();
    output.flush();
  }

  protected abstract Kryo getSharedThreadLocalKyro();

  @Override
  public T read(ObjectDataInput objectDataInput)
      throws IOException {
    InputStream in = (InputStream) objectDataInput;
    InputChunked input = new InputChunked(in, 4096);
    Kryo kryo = getSharedThreadLocalKyro();
    return (T) kryo.readClassAndObject(input);
  }

  @Override
  public void destroy() {
  }

}
```


**`SerializationId.class`**

```
public class SerializationId {
  public static final int ID = 1;

  private SerializationId() {
    throw IllegalStateException("this should never be instantiated, even using reflection");
  }
}
```

**`FooKryoSerializer.class`**

```
public class FooKryoSerializer extends GenericHazelcastKryoSerializer<SomeObject> {

  private static final ThreadLocal<Kryo> kryoThreadLocal
      = new ThreadLocal<Kryo>() {
    @Override
    protected Kryo initialValue() {
      Kryo kryo = new Kryo();
      kryo.register(Foo.class);
      return kryo;
    }
  };

  public SomeObjectKryoSerializer() {
    super(SerializationId.ID);
  }

  @Override
  protected Kryo getSharedThreadLocalKyro() {
    return kryoThreadLocal.get();
  }
}
```



#### Jackson Smile serialization


















## Noteworthy

### no suitable serializer for class

```
com.hazelcast.nio.serialization.HazelcastSerializationException: Failed to serialize 'java.util.ArrayList'] with root cause]
com.hazelcast.nio.serialization.HazelcastSerializationException: There is no suitable serializer for class com.demo.domain.Actor
```

Implement `com.hazelcast.nio.serialization.DataSerializable` interface for class `Actor`:


```
  @Override
  public void writeData(ObjectDataOutput out) throws IOException {
    out.writeInt(id);
    out.writeUTF(username);
    out.writeUTF(email);
  }

  @Override
  public void readData(ObjectDataInput in) throws IOException {
    id = in.readInt();
    username = in.readUTF();
    email = in.readUTF();
  }
```

