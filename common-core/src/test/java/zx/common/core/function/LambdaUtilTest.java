package zx.common.core.function;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.*;

public class LambdaUtilTest {

    @Test
    public void resolveFieldName() {
        assertEquals("name", LambdaUtil.resolveFieldName(Entity::getName));
        assertEquals("firstName", LambdaUtil.resolveFieldName(Entity::getFirstName));
    }

    @Data
    private static class Entity {
        private String name;
        private String firstName;
    }
}
