import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ClassDefinitionTest {

    @Test
    void generate() {
        ClassDefinition clazz = new ClassDefinition(
                "test",
                Collections.singletonList(new MethodDefinition(
                        "test",
                        "String",
                        Collections.singletonList(new MethodParameter("test", "test")),
                        Collections.singletonList("test")
                        )));
        String result = clazz.generate();
        System.out.println(result);
        assertNotNull(result);
    }
}