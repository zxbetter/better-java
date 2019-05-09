package cn.zxbetter.happy.common.web.exception;

/**
 * 消息封装接口
 * <p>
 * 用于规范系统对请求的响应消息
 * <p>
 * 建议在特定的业务模块定义枚举类来实现该接口
 *
 * @author zxbetter
 */
public interface Message {

    /**
     * 获取消息的代码
     *
     * @return 消息代码
     */
    String getCode();

    /**
     * 获取消息描述
     *
     * @return 消息描述
     */
    String getMessage();
}
