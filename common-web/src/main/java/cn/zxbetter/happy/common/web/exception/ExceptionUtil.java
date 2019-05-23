package cn.zxbetter.happy.common.web.exception;

/**
 * 异常工具类
 *
 * @author zxbetter
 */
public class ExceptionUtil {

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
