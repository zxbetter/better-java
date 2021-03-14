package io.github.zxbetter.core.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class BatchProcessorTest {

    @Test
    public void batch() {

        // 1 ～ 100求和
        List<Integer> list = IntStream.range(1, 101).boxed().collect(Collectors.toList());
        Integer result = BatchProcessor.<Integer, Integer>batch(list)
                .action(subList -> subList.stream().mapToInt(Integer::intValue).sum())
                .batchCount(0)
                .result(Integer::sum)
                .setLogger(log)
                .begin();

        Assert.assertEquals(5050, (int) result);

        Integer resultOther = BatchProcessor.<Integer, Integer>batch(list)
                // batchIndex代表当前执行到第几个批次
                .action((batchIndex, subList) -> subList.stream().mapToInt(Integer::intValue).sum())
                .batchCount(15)
                .result(Integer::sum)
                .begin();

        Assert.assertEquals(5050, (int) resultOther);
    }
}
