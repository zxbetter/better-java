package cn.zxbetter.happy.common.web.response;

import cn.zxbetter.happy.common.web.exception.Message;
import cn.zxbetter.happy.common.web.exception.MessageParser;

import java.io.Serializable;
import java.util.List;

/**
 * 统一封装返回结果
 *
 * @author zxbetter
 */
public class ResponseData<T> implements Serializable, MessageParser {

    /**
     * 请求状态
     */
    private boolean status = true;

    /**
     * 状态码
     */
    private String code;

    /**
     * 状态描述信息
     */
    private String message;

    /**
     * 返回的数据
     */
    private List<T> data;

    /**
     * 返回数据的总数 - 用于分页
     */
    private Long total;

    public ResponseData() {}

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<T> getData() {
        return data;
    }

    public void setDatas(List<T> data) {
        this.data = data;
        if (data != null) {
            setTotal((long) data.size());
        }
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public void parseMessage(Message message) {
        setCode(message.getCode());
        setMessage(message.getMessage());
    }
}
