package javapt;

final public class CmdLineParser {
    public CmdLineParser(final String[] str) {
        this.str = str;
    } 

    public <T> T getValue(final String key, T default_value, Class<T> type) {
        int i = 0;

        while ((i < (str.length - 1)) && (str[i].compareTo(key) != 0))
            ++i;

        if (i < (str.length - 1)) {
            if (type == Integer.class) {
                try {
                    return type.cast(Integer.valueOf(str[i + 1]));
                } catch (Exception e) {
                    return default_value;
                }
            }

            if (type == String.class)
                return type.cast(String.valueOf(str[i + 1]));
        }

        return default_value;
    }

    final private String[] str;
}
