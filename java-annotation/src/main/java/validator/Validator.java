package validator;

import constraint.Constraint;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Validator {

    private static Map<String, Field[]> classMap = new ConcurrentHashMap<>();
    private static Map<Field, Annotation[]> atributesMap = new ConcurrentHashMap<>();
    private static Map<Annotation, List<Method>> methodMap = new ConcurrentHashMap<>();

    public static List<Constraint> validate(Object obj) {
        String className = obj.getClass().getName();
        if (!classMap.containsKey(className)) {
            classMap.putIfAbsent(className, obj.getClass().getDeclaredFields());
        }
        return Arrays.stream(classMap.get(className))
                .map(field -> {
                    try {
                        List<Constraint> constrains = new ArrayList<>();
                        if (!atributesMap.containsKey(field)) {
                            field.setAccessible(true);
                            atributesMap.putIfAbsent(field, field.getDeclaredAnnotations());
                        }
                        Object finalValue = field.get(obj);
                        Arrays.stream(atributesMap.get(field)).forEach(annotation -> {
                            try {
                                methodMap.putIfAbsent(
                                        annotation,
                                        Arrays.asList(
                                                annotation.annotationType().getDeclaredMethod("value"),
                                                annotation.annotationType().getDeclaredMethod("validator")));
                                Object defaultValue = invokeMethod(annotation, "value");
                                Object validatorClass = ((Class<?>) invokeMethod(annotation, "validator"))
                                        .newInstance();
                                Boolean valid = (Boolean) validatorClass.getClass()
                                        .getDeclaredMethod("validate", Object.class, Object.class)
                                        .invoke(validatorClass, finalValue, defaultValue);
                                if (!valid) {
                                    Constraint constraint = new Constraint();
                                    constraint.setMessage((String.format((String) annotation.annotationType()
                                            .getMethod("message").invoke(annotation), finalValue)));
                                    constraint.setFieldName(String.format("%s.%s", className, field.getName()));
                                    constrains.add(constraint);
                                }
                            } catch (InvocationTargetException | IllegalAccessException | NoSuchMethodException
                                    | InstantiationException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        return constrains;
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private static Object invokeMethod(Annotation annotation, String validator)
            throws IllegalAccessException, InvocationTargetException {
        return Objects.requireNonNull(
                methodMap.get(annotation)
                        .stream()
                        .filter(method -> method.getName().equalsIgnoreCase(validator))
                        .findFirst()
                        .orElse(null))
                .invoke(annotation);
    }
}
