package zx.common.web.exception;

/**
 * 消息提供类
 * <p>
 * 定义了一系列常用的消息
 *
 * @author zxbetter
 */
public class MessageProvider {

    /**
     * 自定义消息的描述
     *
     * @param msg     消息实体
     * @param message 消息描述
     * @return 定制后的消息实体
     */
    public static Message customize(DefaultMessage msg, String message) {
        return new CustomizeMessage(msg, message);
    }

    /**
     * 对于同一类消息，可能会有不同的 "消息描述"
     * <p>
     * {@link CustomizeMessage} 提供了对 {@link DefaultMessage} 里定义的消息的 "消息描述" 的修改
     * <p>
     * TIP: 只在必要的时候使用，通常应该使用 {@link DefaultMessage}
     */
    private static class CustomizeMessage implements Message {
        private String code;
        private String message;

        CustomizeMessage(Message msg, String message) {
            this.code = msg.getCode();
            this.message = message;
        }

        @Override
        public String getCode() {
            return this.code;
        }

        @Override
        public String getMessage() {
            return this.message;
        }
    }
}
