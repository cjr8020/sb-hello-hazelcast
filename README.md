# sb-hello-hazelcast


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

