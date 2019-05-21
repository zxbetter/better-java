package cn.zxbetter.happy.common.core.logger;

import org.slf4j.Logger;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * 日志工具类
 *
 * @author zxbetter
 */
public class LogUtil {

    /**
     * 常用于工具类打印日志
     * <p>
     * 通常工具类应该使用调用者的日志对象（便于日志收集）。如果调用者没有提供日志对象，则使用工具类自带的日志对象。
     *
     * @param logger     调用者的日志对象
     * @param defaultLog 工具类的日志对象
     * @param print      打印日志的动作
     */
    public static void log(Logger logger, Logger defaultLog, Consumer<Logger> print) {
        if (Objects.nonNull(logger)) {
            print.accept(logger);
        } else if (Objects.nonNull(defaultLog)) {
            print.accept(defaultLog);
        }
    }
}
