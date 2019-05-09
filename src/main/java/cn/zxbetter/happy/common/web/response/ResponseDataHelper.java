package cn.zxbetter.happy.common.web.response;

import cn.zxbetter.happy.common.web.exception.DefaultMessage;
import cn.zxbetter.happy.common.web.exception.Message;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * {@link ResponseData} 的工具类
 *
 * @author zxbetter
 */
public class ResponseDataHelper {
    private ResponseDataHelper() {}

    public static ResponseData success() {
        return success(null);
    }

    public static <T> ResponseData success(T data) {
        return build(true, null, data, null);
    }

    public static <T> ResponseData success(List<T> dataList) {
        return build(true, null, null, dataList);
    }

    public static ResponseData error(Message message) {
        return build(false, message, null, null);
    }

    private static <T> ResponseData build(boolean status, Message message, T data, List<T> dataList) {
        ResponseData<T> response = new ResponseData<>();
        response.setStatus(status);

        // 没有消息，则根据状态获取默认的消息。
        if (message == null) {
            message = status ? DefaultMessage.SUCCESS : DefaultMessage.SERVER_ERROR;
        }

        response.parseMessage(message);

        if (Objects.nonNull(data)) {
            List<T> list = new ArrayList<>();
            list.add(data);
            response.setDatas(list);
        } else if (Objects.nonNull(dataList)) {
            response.setDatas(dataList);
        } else {
            response.setDatas(new ArrayList<>());
        }

        return response;
    }
}
