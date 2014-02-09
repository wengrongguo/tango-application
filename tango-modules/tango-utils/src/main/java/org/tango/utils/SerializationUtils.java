package org.tango.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.ObjectStreamClass;
import java.io.ObjectInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
* 序列化工具类
*
* @author tAngo
*/
public final class SerializationUtils {

    private SerializationUtils() {
    }

    /**
     * 对象克隆
     *
     * @param object 实现Serializable的对象
     * @return 克隆对象
     */
    public static Object clone(Serializable object) throws IOException {
        return deserialize(serialize(object));
    }

    /**
     * 序列化到输出流
     *
     * @param obj          实现Serializable的对象
     * @param outputStream 输出流
     */
    public static void serialize(Serializable obj, OutputStream outputStream) throws IOException {
        if (outputStream == null) {
            throw new IllegalArgumentException("The OutputStream must not be null");
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(outputStream);
            out.writeObject(obj);
        } finally {
            try {
                if (out != null)
                    out.close();
            } catch (IOException ignored) {
            }
        }
    }

    /**
     * 序列化到字节数组
     *
     * @param obj 实现Serializable的对象
     * @return 字节数组
     */
    public static byte[] serialize(Serializable obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(obj, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    /**
     * 输入流反序列化到对象
     *
     * @param inputStream 输入流
     * @return 对象
     */
    public static Object deserialize(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("The InputStream must not be null");
        }
        CustomObjectInputStream in = null;
        try {
            in = new CustomObjectInputStream(inputStream);
            return in.readObject();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ex) {
            }
        }
    }

    /**
     * 字节数组反序列化到对象
     *
     * @param objectData 字节数组
     * @return 对象
     */
    public static Object deserialize(byte[] objectData) {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectData);
        return deserialize(byteArrayInputStream);
    }

    private static final class CustomObjectInputStream extends ObjectInputStream {

        public CustomObjectInputStream(InputStream in) throws IOException {
            super(in);
        }

        protected Class<?> resolveClass(ObjectStreamClass v) throws IOException, ClassNotFoundException {
            String className = v.getName();
            Class<?> resolvedClass = null;
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            try {
                resolvedClass = loader.loadClass(className);
            } catch (ClassNotFoundException e) {
                resolvedClass = super.resolveClass(v);
            }
            return resolvedClass;
        }
    }
}
