package servicesystem.bruteforcer.patterns;

/**
 * Created by Spider on 01.03.2015.
 */
public class ProvidersFilenamePattern extends FilenamePattern {
    public ProvidersFilenamePattern(Object... data) {
        super(data);
        type = "providers";
        args = new String[]{"p"};
    }
}
