package com.java.netty.kit;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtobufIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
@SuppressWarnings("unchecked")
public final class ProtostuffKit {

    private static final Map<Class<?>, Schema> MAP    = new ConcurrentHashMap<>();
    private static       LinkedBuffer          BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    /**
     * 序列化
     *
     * @param object 对象实例
     * @return 字节数组
     */
    public static <T> byte[] serializer(T object) {
        if (object == null) {
            return null;
        }
        Class<T> clazz  = (Class<T>) object.getClass();
        Schema   schema = getSchema(clazz);
        byte[]   bytes;
        try {
            bytes = ProtobufIOUtil.toByteArray(object, schema, BUFFER);
        } finally {
            BUFFER.clear();
        }
        return bytes;
    }

    /**
     * 反序列化
     *
     * @param <T> 范性
     * @return 对应范性的实例
     */
    public static <T> T deserialize(byte[] data, Class<T> tClass) {
        Schema<T> schema = getSchema(tClass);
        T         t      = schema.newMessage();
        ProtobufIOUtil.mergeFrom(data, t, schema);
        return t;
    }

    private static <T> Schema<T> getSchema(Class<T> tClass) {
        // 优先取缓存的schema
        Schema cacheSchema = MAP.get(tClass);
        if (cacheSchema != null) {
            return cacheSchema;
        }
        Schema schema = RuntimeSchema.getSchema(tClass);
        if (schema != null) {
            MAP.put(tClass, schema);
        }
        return schema;
    }
}
