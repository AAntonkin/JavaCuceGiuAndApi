package framework.utils;

public class StringParser {

    public static String upper(String string, int charPositionToUpper) {
        char[] c = string.toCharArray();
        c[charPositionToUpper] -= 32;
        return (c[charPositionToUpper] > 64 && c[charPositionToUpper] < 91) ? new String(c) : string;
    }

    public static String delete(String value, String... remove) {
        for (String ch : remove) {
            value = value.replace(ch, "");
        }
        return value;
    }
}
