package framework.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Set;

@Slf4j
public class ReflectEx extends Reflections {

    public ReflectEx(String pkgName) {
        super(pkgName);
    }

    public ReflectEx() {
        super("product");
    }

    public static Object getElementByName(Object object, String declaredElementName, Object... args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method;
        Object obj = object;
        if (args.length >= 1) {
            Class<?>[] argClasses = Arrays.stream(args).map(Object::getClass).toArray(Class<?>[]::new);
            method = obj.getClass().getMethod(declaredElementName, argClasses);
        } else {
            method = obj.getClass().getMethod(declaredElementName);
        }
        return method.invoke(obj, args);
    }

    public static Object getElementByNameOrNull(Object object, String declaredElementName, Object... args) {
        try {
            return getElementByName(object, declaredElementName, args);
        } catch (NoSuchElementException |
                NoSuchMethodException |
                IllegalAccessException |
                InvocationTargetException e) {
            String message = e.getClass().getSimpleName() + ":";
            Throwable th = e.getCause();
            int tires = 4;
            while (th != null && tires-- > 0 && th.getMessage().length() > 0) {
                message += "\n" + new String(new char[(4 - tires)]).replace("\0", "- ").concat("> ") + th.getMessage().split("\n")[0];
                th = th.getCause();
            }
            if (e.getMessage() == null)
                log.error(message);
            else
                log.error(e.getClass().getSimpleName() + ": " + e.getMessage());
            return null;
        }
    }

    @SneakyThrows
    public Object getWindowByName(String windowName, Class clazz) {
        Class ob2 = getClassByName(windowName, clazz);
        return (ob2 == null) ? null : ob2.newInstance();
    }

    @SneakyThrows
    public Class getClassByName(String windowName, Class clazz) {
        Set<Class> allClasses = this.getSubTypesOf(clazz);
        return allClasses.stream().filter(c1 ->
                        c1.getSimpleName().equals(windowName)).findFirst()
                .orElse(null);
    }

}
