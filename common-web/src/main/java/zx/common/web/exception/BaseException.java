package zx.common.web.exception;

import zx.common.core.logger.LogUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;

import java.util.Objects;

/**
 * 统一封装异常
 *
 * @author zxbetter
 */
@Slf4j
public class BaseException extends RuntimeException {

    private static final String LOG_TEMPLATE = "状态码:[{}], 异常消息: {}";

    /**
     * 异常消息
     */
    private Message msg;

    protected BaseException(Message message) {
        super(message.getMessage());
        this.msg = message;
    }

    public Message getMsg() {
        return msg;
    }

    /**
     * 1. 解析 message
     * <p>
     * 2. 抛出 message 的异常消息
     *
     * @param messageParser 解析对象
     * @param message  异常消息
     * @param logger 日志对象
     * @throws BaseException 异常
     */
    public static void throwAndParse(MessageParser messageParser, Message message, Logger logger) throws BaseException {
        if (Objects.isNull(message)) {
            return;
        }

        // 解析消息
        if (Objects.nonNull(messageParser)) {
            messageParser.parseMessage(message);
        }

        LogUtil.log(logger, LOG, log -> log.error(LOG_TEMPLATE, message.getCode(), message.getMessage()));

        throw new BaseException(message);
    }

    /**
     * 抛出异常
     *
     * @param message 异常消息
     * @throws BaseException 抛出的异常
     */
    public static void throwException(Message message, Logger logger) throws BaseException {
        throwAndParse(null, message, logger);
    }
}
