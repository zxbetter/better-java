package zx.common.core.batch;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class BatchOperateUtilTest {

    @Test
    public void batch() {
        List<Integer> list = IntStream.range(1, 100).boxed().collect(Collectors.toList());
        Integer result = BatchOperateUtil.<Integer, Integer>batch(list)
                .action(subList -> subList.stream().mapToInt(Integer::intValue).sum())
                .batchCount(5)
                .result(Integer::sum)
                .setLogger(LOG)
                .begin();
        System.out.println(result);
    }
}
