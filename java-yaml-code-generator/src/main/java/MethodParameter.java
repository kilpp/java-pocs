import lombok.Getter;

public class MethodParameter {
    String name;
    String type;
//    @Getter
//    Class<?> clazz;

    public MethodParameter(String name, String type) {
        this.name = name;
        this.type = type;
//        try {
//            this.clazz = Class.forName("java.lang." + type);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException("Cannot find class for parameter name " + name);
//        }

    }

    @Override
    public String toString() {
        return "MethodParameter{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String generate() {
        return type + " " + name;
    }
}
