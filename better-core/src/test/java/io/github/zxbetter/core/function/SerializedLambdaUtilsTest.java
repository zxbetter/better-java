package io.github.zxbetter.core.function;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

public class SerializedLambdaUtilsTest {

    @Test
    public void resolveFieldName() {
        assertEquals("name", SerializedLambdaUtils.resolveFieldName(Entity::getName));
        assertEquals("boy", SerializedLambdaUtils.resolveFieldName(Entity::isBoy));

        // 解析非getter/setter方法会抛出异常
        assertThrows(RuntimeException.class, () -> SerializedLambdaUtils.resolveFieldName(Entity::callName));
    }


    @Test(expected = RuntimeException.class)
    public void resolve() {
        // 解析非Lambda表达式会抛出异常
        SerializedLambdaUtils.resolve(1);
    }

    @Data
    private static class Entity {
        private String name;
        private boolean boy;

        public String callName() {
            return this.name;
        }
    }
}
