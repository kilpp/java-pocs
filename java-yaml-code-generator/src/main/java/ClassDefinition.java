import lombok.Getter;

import java.util.List;

public class ClassDefinition {

    @Getter
    String name;

    @Getter
    List<String> imports;

    @Getter
    List<MethodDefinition> methods;

    public ClassDefinition(String name, List<String> imports, List<MethodDefinition> methods) {
        this.name = name;
        this.imports = imports;
        this.methods = methods;
    }

    @Override
    public String toString() {
        return "ClassDefinition{" +
                "name='" + name + '\'' +
                ", methods=" + methods +
                '}';
    }

    public String generate() {
        String clazz = "";
        for (int i = 0; i < imports.size(); i++) {
            clazz += imports.get(i) + "\n";
        }
        clazz += "import java.lang.reflect.Field;\n";

        clazz += "public class " + this.name + " { \n";
        for (int i = 0; i < methods.size(); i++) {
            MethodDefinition method = methods.get(i);
            clazz += method.generate();
        }
        clazz += "\n}\n";
        return clazz;
    }

}
