package zx.common.web.exception;

/**
 * {@link Message} 消息的解析器
 *
 * @author zxbetter
 */
public interface MessageParser {

    /**
     * 解析消息
     *
     * @param message 消息
     */
    void parseMessage(Message message);
}
