package framework.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Framework {
    public static String getParentMethodName(String... excludeNames) {
        String methodName = "";
        List<String> exclude = Arrays.asList(excludeNames);
        try {
            String projectFolderName = new File(System.getProperty("user.dir")).getName();
            StackTraceElement[] stack = Thread.currentThread().getStackTrace();
            for (int counter = 3; counter < stack.length; counter++) {
                StackTraceElement el = stack[counter];
                Class clazz = Class.class;
                try {
                    clazz = Class.forName(el.getClassName());
                } catch (ClassNotFoundException e) {
                }
                methodName = clazz.getSimpleName();
                URL resource = methodName.contains("$$") ? null : clazz.getResource("");
                if (resource != null && resource.getFile().contains(projectFolderName) && !exclude.contains(el.getMethodName())) {
                    methodName += ":" + el.getLineNumber() + "[" + el.getMethodName() + "()]";
                    break;
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return methodName;
    }

    public static void waitABit(final long delayInMilliseconds) {
        try {
            Thread.sleep(delayInMilliseconds);
        } catch (InterruptedException e) {
            log.warn("Wait a bit method was interrupted.", e);
        }
    }
}
