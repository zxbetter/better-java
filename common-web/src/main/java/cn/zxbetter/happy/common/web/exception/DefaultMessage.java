package cn.zxbetter.happy.common.web.exception;

/**
 * 每个 code 对应一类消息
 * <p>
 * 提供默认的消息实现
 *
 * @author zxbetter
 */
public enum DefaultMessage implements Message {
    /**
     * 处理成功
     */
    SUCCESS("200", "成功"),

    /**
     * 认证失败
     */
    AUTH_FAIL("401", "认证失败"),

    /**
     * 禁止访问
     */
    FORBIDDEN("403", "禁止访问"),

    /**
     * 服务器内部错误
     */
    SERVER_ERROR("500", "服务器内部错误"),

    /**
     * 数据库异常
     */
    DB_ERROR("600", "数据库异常"),

    /**
     * 分布式锁异常
     */
    LOCK_ERROR("700", "数据被其他程序占用，请稍后重试"),

    /**
     * 批量处理时标记单个处理成功
     */
    SUCCESS_ITEM("2000", "成功"),

    PARAM_REQUIRED("2001", "缺少必要的请求参数"),

    PARAM_ERROR("2002", "请求参数校验错误"),
    ;
    private String code;
    private String message;

    DefaultMessage(String code, String message) {
        this.code = code;
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

    /**
     * 异常工具类
     *
     * @author zxbetter
     */
    public static class ExceptionUtil {

        /**
         * 获取异常的 root case
         *
         * @param throwable 异常信息
         * @return root case
         */
        public static Throwable getRootCause(final Throwable throwable) {
            Throwable thr = throwable;
            while (thr.getCause() != null) {
                thr = thr.getCause();
            }
            return thr;
        }
    }
}
