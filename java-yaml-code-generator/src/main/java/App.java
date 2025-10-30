import test.Loan;
import test.LoanDTO;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.stream.Collectors;

public class App {


    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        CodeGenerator codeGenerator = new CodeGenerator("test.yaml");
        List<ClassDefinition> clazz = codeGenerator.load();
        clazz.forEach(codeGenerator::generate);

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int com = compiler.run(null, null, null, codeGenerator.getPaths().get("Converter"));
        if(0 != com) {
            throw new RuntimeException("cannot compile class");
        }
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{ codeGenerator.getRoots().get("Converter")});
        Class<?> dynamicClass = Class.forName("Converter", true,classLoader);
        Object dynamicInstance = dynamicClass.getDeclaredConstructor().newInstance();
//        List<Class<?>> paramTypes = clazz.get(0).getMethods().get(0).getParameters().stream().map(MethodParameter::getClazz).collect(Collectors.toList());
//        paramTypes.toArray(new Class[0])
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setAmount(100D);

        LoanDTO result = (LoanDTO) dynamicClass.getMethod("convert", Loan.class).invoke(dynamicInstance, loan);
        System.out.println(result);
    }

}
//java assist x java compiler
//bytebuddy
