package zx.common.core.function;

import java.io.Serializable;
import java.util.function.Function;

/**
 * 支持序列化的 ${@link Function}
 * @param <T>
 * @param <R>
 *
 * @author zhangxin
 */
@FunctionalInterface
public interface SFunction<T, R> extends Function<T, R>, Serializable {
}
