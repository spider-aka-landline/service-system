package servicesystem.bruteforcer.patterns;

/**
 * Created by Spider on 01.03.2015.
 */
public abstract class FilenamePattern {
    String type;
    String[] args;
    Object[] parameters;
    public static final String delimiter = "_";
    public static final String extension = "txt";
    public static final String digitsPattern = "[\\d]";

    protected FilenamePattern(Object[] data) {
        parameters = data;
    }

    public String getFilenamePattern() {
        String[] params = new String[args.length];
        for (int i = 0; i < params.length; i++) {
            params[i] = digitsPattern;
        }
        return getFilename(params);
    }

    public String getFilename() {
        return getFilename(parameters);
    }

    private String getFilename(Object[] params) {
        if (args.length != params.length)
            throw new IllegalArgumentException("Parameters array has wrong size");

        StringBuilder sb = new StringBuilder(type);
        for (int i = 0; i < args.length; i++) {
            sb.append(delimiter).append(args[i]).append(params[i]);
        }
        sb.append('.').append(extension);
        return sb.toString();
    }


}
