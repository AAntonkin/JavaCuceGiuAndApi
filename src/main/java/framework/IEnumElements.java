package framework;

import lombok.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface IEnumElements {

    String getText();

    String name();

    static <T extends Enum<T>> T getByText(@NonNull final String text, @NonNull Class<T> type, boolean ignoreCase) {
        T[] v = type.getEnumConstants();
        List<T> value = new ArrayList<>();
        if (v != null)
            value = Arrays.stream(v)
                    .filter(a -> {
                        String s = ((IEnumElements) a).getText().trim();
                        String formatedText = text.replace(":", "");
                        if (ignoreCase)
                            return formatedText.equalsIgnoreCase(s);
                        else
                            return formatedText.equals(s);
                    })
                    .collect(Collectors.toList());
        return value != null && value.size() > 0 ? value.get(0) : null;
    }
}



