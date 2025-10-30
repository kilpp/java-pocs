import lombok.Getter;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CodeGenerator {

    private String path;

    @Getter
    private Map<String, String> paths = new HashMap<>();

    @Getter
    private Map<String, URL> roots = new HashMap<>();


    public CodeGenerator(String path) {
        this.path = path;
    }

    public void generate(ClassDefinition clazz) {
        try {
            File root = Files.createTempDirectory("generated").toFile();
            File sourceFile = new File(root, clazz.name + ".java");
            Files.writeString(sourceFile.toPath(), clazz.generate());
            paths.put(clazz.getName(), sourceFile.getPath());
            roots.put(clazz.name, root.toURI().toURL());
        } catch (IOException e) {
            throw new RuntimeException("error writing files");
        }
    }

    public List<ClassDefinition> load() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (InputStream inputStream = classLoader.getResourceAsStream(path)) {
            Yaml yaml = new Yaml();
            List<Map<String, Object>> definitions = yaml.load(inputStream);
            return definitions.stream().map(c -> {
                List<Map<String, Object>> methods = (List<Map<String, Object>>) c.get("methods");
                return new ClassDefinition(
                        (String) c.get("className"),
                        (List<String>) c.get("imports"),
                        methods.stream().map(m -> {
                                    List<Map<String, Object>> parameters = (List<Map<String, Object>>) m.get("parameters");
                                    return new MethodDefinition(
                                            (String) m.get("name"),
                                            (String) m.get("returnType"),
                                            parameters.stream().map(p -> new MethodParameter((String) p.get("name"), (String) p.get("type"))).toList(),
                                            (List<String>) m.get("body"));
                                })
                                .toList()
                );
            }).toList();
        } catch (IOException e) {
            throw new RuntimeException("Error creating class definitions");

        }
    }
}
