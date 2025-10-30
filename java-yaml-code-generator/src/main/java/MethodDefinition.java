import lombok.Getter;
import test.LoanDTO;

import java.lang.reflect.Field;
import java.util.List;

public class MethodDefinition {

    public MethodDefinition(String name, String returnType, List<MethodParameter> parameters, List<String> body) {
        this.name = name;
        this.returnType = returnType;
        this.parameters = parameters;
        this.body = body;
    }

    String name;
    String returnType;
    @Getter
    List<MethodParameter> parameters;
    List<String> body;

    public String generate() {
        String method = "\n";
        method += "     public " + returnType + " " + name + "(";
        for (int i = 0; i < parameters.size(); i++) {
            method += parameters.get(i).generate();
            if (i < parameters.size() - 1) {
                method += ", ";
            } else {
                method += ") {\n";
            }
        }

        method += "try {\n";

        method += "Class<?> dynamicClassTo = Class.forName(" + "\"" + "test." + returnType + "\"" + ");\n" +
                "            Object to = dynamicClassTo.getDeclaredConstructor().newInstance();\n" +
                "            for (int i = 0; i<to.getClass().getDeclaredFields().length;i++) {\n" +
                "                Field f = to.getClass().getDeclaredFields()[i];\n" +
                "                Field d =" + parameters.get(0).name + ".getClass().getDeclaredField(f.getName());\n" +
                "                d.setAccessible(true);\n" +
                "                f.setAccessible(true);\n" +
                "                f.set(to, d.get(" + parameters.get(0).name + "));\n" +
                "            }\n" +
                "            return (" + returnType + ") to;\n";

        method += "} catch (Exception e) {\n" +
                "     System.out.println(\"ERROR \" + e);\n" +
                "     throw new RuntimeException(e);\n" +
                "  }";
        method += "\n     }\n";
        return method;
    }

    @Override
    public String toString() {
        return "MethodDefinition{" +
                "name='" + name + '\'' +
                ", returnType='" + returnType + '\'' +
                ", parameters=" + parameters +
                ", body=" + body +
                '}';
    }


}
