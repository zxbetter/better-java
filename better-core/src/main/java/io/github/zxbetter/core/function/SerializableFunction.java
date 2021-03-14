package io.github.zxbetter.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 支持序列化的 ${@link Function}
 * @param <T>
 * @param <R>
 *
 * @author zxbetter
 */
@FunctionalInterface
public interface SerializableFunction<T, R> extends Function<T, R>, Serializable {
}
