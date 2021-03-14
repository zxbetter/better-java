package io.github.zxbetter.core.function;

import java.io.Serializable;
import java.lang.invoke.SerializedLambda;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * SerializedLambda工具类
 *
 * @author zxbetter
 */
public final class SerializedLambdaUtils {

    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> SERIALIZED_LAMBDA_MAP_CACHE = new ConcurrentHashMap<>();

    private SerializedLambdaUtils() {
    }

    /**
     * 通过Lambda表达式获取对象的字段名称
     *
     * @param fieldMethodOfGetterOrSetter 字段Getter/Setter方法对应的Lambda表达式
     * @return 字段名称
     */
    public static <T> String resolveFieldName(SerializableFunction<T, ?> fieldMethodOfGetterOrSetter) {
        SerializedLambda serializedLambda = resolve(fieldMethodOfGetterOrSetter);
        return methodToProperty(serializedLambda.getImplMethodName());
    }

    /**
     * 通过对象方法名称获取字段名称
     *
     * @param name 方法名称
     * @return 字段名称
     * @see org.apache.ibatis.reflection.property.PropertyNamer#methodToProperty(String)
     */
    private static String methodToProperty(String name) {
        if (name.startsWith("is")) {
            name = name.substring(2);
        } else {
            if (!name.startsWith("get") && !name.startsWith("set")) {
                throw new RuntimeException("Error parsing property name '" + name + "'.  Didn't start with 'is', 'get' or 'set'.");
            }

            name = name.substring(3);
        }

        if (name.length() == 1 || name.length() > 1 && !Character.isUpperCase(name.charAt(1))) {
            name = name.substring(0, 1).toLowerCase(Locale.ENGLISH) + name.substring(1);
        }

        return name;
    }

    /**
     * 解析可序列化的lambda表达式。
     *
     * 使用缓存
     *
     * @param serializableLambda 该Lambda表达式对应的函数式接口需要继承 ${@link Serializable}
     * @return 序列化的代理对象
     */
    public static SerializedLambda resolve(Serializable serializableLambda) {
        Objects.requireNonNull(serializableLambda);

        Class<?> clazz = serializableLambda.getClass();
        return Optional.ofNullable(SERIALIZED_LAMBDA_MAP_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda serializedLambda = resolveLambda(serializableLambda);
                    SERIALIZED_LAMBDA_MAP_CACHE.put(clazz, new WeakReference<>(serializedLambda));
                    return serializedLambda;
                });
    }

    /**
     * 解析可序列化的lambda表达式
     *
     * @param lambda 表达式
     * @return 序列化的代理对象
     */
    private static SerializedLambda resolveLambda(Serializable lambda) {
        try {
            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(Boolean.TRUE);
            return (SerializedLambda) writeReplace.invoke(lambda);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
