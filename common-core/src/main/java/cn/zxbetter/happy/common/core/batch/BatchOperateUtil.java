package cn.zxbetter.happy.common.core.batch;

import org.slf4j.Logger;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

/**
 * 分批处理工具类
 *
 * @author zhangxin
 */
public class BatchOperateUtil {

    public static <T, R> BatchBuilder<T, R> batch(List<T> batchDataList) {
        return new BatchBuilder<>(batchDataList);
    }

    /**
     * 默认每个批次处理的条数
     */
    private static final int DEFAULT_BATCH_COUNT_1000 = 1000;

    /**
     * 批处理器
     *
     * @param <T> 定义批处理数据的类型
     * @param <R> 定义批处理的返回类型
     */
    public static class BatchBuilder<T, R> {

        /**
         * 分批处理的数据
         */
        private List<T> batchDataList;

        /**
         * 每个批次处理的数据个数
         */
        private int batchCount = DEFAULT_BATCH_COUNT_1000;

        /**
         * 打印日志
         */
        private Logger logger;

        /**
         * 逻辑处理
         */
        private BiFunction<Integer, List<T>, R> actionFunction;

        /**
         * 合并结果集
         */
        private BinaryOperator<R> mergeFunction;

        /**
         * 批处理器的构造函数
         *
         * @param batchDataList 批处理的数据
         */
        BatchBuilder(List<T> batchDataList) {
            this.batchDataList = batchDataList;
        }

        /**
         * 指定每个批次处理的数据个数
         *
         * @param batchCount 每个批次处理的数据个数
         * @return 批处理器
         */
        public BatchBuilder<T, R> batchCount(int batchCount) {
            this.batchCount = batchCount;
            return this;
        }

        /**
         * 指定批处理器的日志对象
         *
         * @param logger 日志对象
         * @return 批处理器
         */
        public BatchBuilder<T, R> setLogger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * 指定批处理器的逻辑处理对象
         *
         * @param actionFunction 逻辑处理对象
         * @return 批处理器
         */
        public BatchBuilder<T, R> action(BiFunction<Integer, List<T>, R> actionFunction) {
            this.actionFunction = actionFunction;
            return this;
        }

        /**
         * 指定批处理器的逻辑处理对象
         *
         * @param actionFunction 逻辑处理对象
         * @return 批处理器
         */
        public BatchBuilder<T, R> action(Function<List<T>, R> actionFunction) {
            this.actionFunction = (batchIndex, subList) -> actionFunction.apply(subList);
            return this;
        }

        /**
         * 指定批处理器的结果处理对象
         *
         * @param mergeFunction 处理结果集对象
         * @return 批处理器
         */
        public BatchBuilder<T, R> result(BinaryOperator<R> mergeFunction) {
            this.mergeFunction = mergeFunction;
            return this;
        }

        /**
         * 执行批处理操作
         *
         * @return 处理结果
         */
        public R begin() {
            // 必要的校验
            Objects.requireNonNull(this.batchDataList, "没有指定分批处理的数据，不能执行批处理操作");
            Objects.requireNonNull(this.actionFunction, "没有指定处理逻辑，可以调用相关的 action 方法指定");

            // 处理数据的结果
            R result = null;

            // 是否需要打印日志
            boolean isDebug = Objects.nonNull(logger);
            // 批次处理开始时间
            LocalDateTime start = null,
                    // 结束时间
                    end;

            if (isDebug) {
                start = LocalDateTime.now();
                logger.debug("----> 执行批处理程序，开始时间 {}", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")));
            }

            // 处理的数据的数量
            int size = this.batchDataList.size(),
                    //下标从
                    fromIndex,
                    //下标至
                    toIndex;

            // 每个批次处理的数据量
            if (this.batchCount <= 0) {
                this.batchCount = DEFAULT_BATCH_COUNT_1000;
            }

            if (isDebug) {
                logger.debug("批处理的数据总量是 {}， 每个批次处理的数据量是 {}", size, this.batchCount);
            }

            // 分批次处理
            for (int i = 0, batch = (int) Math.ceil(size * 1.0 / this.batchCount); i < batch; i++) {
                fromIndex = i * this.batchCount;
                toIndex = (i + 1) * this.batchCount;
                if (toIndex > size) {
                    toIndex = size;
                }

                if (isDebug) {
                    logger.debug("当前批次 {}, 下标从 {}， 下标至 {}", i, fromIndex, toIndex);
                }

                // 处理数据
                if (Objects.isNull(result) || Objects.isNull(this.mergeFunction)) {
                    // 直接获取批次的结果
                    result = this.actionFunction.apply(i, this.batchDataList.subList(fromIndex, toIndex));
                } else {
                    // 对每次处理的结果进行处理
                    result = this.mergeFunction.apply(result, this.actionFunction.apply(i, this.batchDataList.subList(fromIndex, toIndex)));
                }
            }

            if (isDebug) {
                end = LocalDateTime.now();
                logger.debug("<---- 批处理程序执行完成， 结束时间 {}，耗时 {} 秒", start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.SSS")), Duration.between(start, end).toMillis() / 1000.0);
            }

            // 返回结果
            return result;
        }
    }
}
