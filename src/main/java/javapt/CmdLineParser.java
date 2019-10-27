package javapt;

/**
 * Comand line options parser.
 */
 public final class CmdLineParser {
    /** 
     * Creates the parser given the command line options string. 
     * @param str Command line options string. 
     */
    public CmdLineParser(final String[] str) {
        this.str = str;
    } 

    /** 
     * Returns the value of the specified command line option.
     * @param option Option.
     * @param default_value The default value o the option in the case the value is not provided in the command line option. 
     * @param type The basic type of the option. Accepted values are: Integer.class and String.class.
     * @return The value of the corresponding input option. If the input option is not set in the command line, 
     * its default value is returned.
     */
    public final <T> T getValue(final String option, final T default_value, final Class<T> type) {
        int i = 0;

        while ((i < (str.length - 1)) && (str[i].compareTo(option) != 0))
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

    private final String[] str;
}
