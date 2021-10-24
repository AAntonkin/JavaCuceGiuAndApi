package product.Handlers;

import framework.utils.StringParser;
import lombok.NonNull;
import net.serenitybdd.core.Serenity;

import java.util.*;
import java.util.stream.Collectors;

public class DataSupplier {

    public static final String QA_TEST_PREFIX = "aqa_item";

    public static String parse(@NonNull String inputString) {
        String out = inputString;
        Class[] classes = {TestVariables.class, VerificationVariables.class};
        if (out != null) {

            for (Class clazz : classes)
                for (ITestVariables var : Arrays.stream(((ITestVariables[]) clazz.getEnumConstants()))
                        .filter(enumValue -> inputString.contains("<" + enumValue.name() + ">") || inputString.contains("<" + enumValue.getText() + ">"))
                        .filter(enumValue -> enumValue.getString() != null)
                        .collect(Collectors.toList())) {
                    out = out
                            .replace("<" + var.name() + ">", Objects.requireNonNull(var.getString()))
                            .replace("<" + var.getText() + ">", Objects.requireNonNull(var.getString()));
                }
        }
        if (out.contains("<") && out.contains(">")) {
            String possibleSavedVar = StringParser.delete(out, "<", ">");
            if (Serenity.getCurrentSession().containsKey(possibleSavedVar))
                out = Serenity.getCurrentSession().get(StringParser.delete(out, "<", ">")).toString();
        }
        return out;
    }

    public static String findValue(LinkedHashMap<String, Object> map, String key) {
        Optional<Object> res = Optional.ofNullable(map.get(key));
        if (!res.isPresent()) {
            for (String path : map.keySet()) {
                if (map.get(path) instanceof Map) {
                    res = Optional.ofNullable(findValue((LinkedHashMap<String, Object>) map.get(path), key));
                    if (res.isPresent()) break;
                }
            }
        }
        return res.get().toString();
    }
}
