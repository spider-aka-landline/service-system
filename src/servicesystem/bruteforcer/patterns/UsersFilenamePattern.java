package servicesystem.bruteforcer.patterns;

/**
 * Created by Spider on 01.03.2015.
 */
public class UsersFilenamePattern extends FilenamePattern {

    public UsersFilenamePattern(Object... data) {
        super(data);
        type = "users";
        args = new String[]{"u"};
    }
}
