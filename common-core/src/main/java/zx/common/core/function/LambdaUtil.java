package zx.common.core.function;

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
 * lambda 表达式工具类
 *
 * @author zhangxin
 */
public final class LambdaUtil {
    /**
     * SerializedLambda 反序列化缓存
     */
    private static final Map<Class<?>, WeakReference<SerializedLambda>> FUNC_CACHE = new ConcurrentHashMap<>();

    private LambdaUtil() {
    }

    /**
     * 获取对象的字段名称
     *
     * @param fieldMethod 字段方法对应的 lambda 表达式
     * @return 字段名称
     */
    public static <T> String resolveFieldName(SFunction<T, ?> fieldMethod) {
        SerializedLambda serializedLambda = resolve(fieldMethod);
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
     * 解析 lambda 表达式，使用缓存
     *
     * @param lambda 表达式
     * @return 序列化的代理对象
     */
    public static SerializedLambda resolve(Serializable lambda) {
        Class<?> clazz = lambda.getClass();
        return Optional.ofNullable(FUNC_CACHE.get(clazz))
                .map(WeakReference::get)
                .orElseGet(() -> {
                    SerializedLambda serializedLambda = resolveHard(lambda);
                    FUNC_CACHE.put(clazz, new WeakReference<>(serializedLambda));
                    return serializedLambda;
                });
    }

    /**
     * 解析 lambda 表达式
     *
     * @param lambda 表达式
     * @return 序列化的代理对象
     */
    private static SerializedLambda resolveHard(Serializable lambda) {
        checkClassType(lambda);
        try {
            Method writeReplace = lambda.getClass().getDeclaredMethod("writeReplace");
            writeReplace.setAccessible(Boolean.TRUE);
            return (SerializedLambda) writeReplace.invoke(lambda);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 校验入参，只能是 lambda 表达式
     *
     * @param lambda lambda 表达式
     */
    private static void checkClassType(Serializable lambda) {
        if (Objects.isNull(lambda)) {
            throw new RuntimeException("lambda 工具类：序列化入参只能是 lambda 表达式");
        }
        Class<?> clazz = lambda.getClass();
        if (!clazz.isSynthetic() || !clazz.toString().contains("$Lambda$")) {
            throw new RuntimeException("lambda 工具类：序列化入参只能是 lambda 表达式");
        }
    }
}
