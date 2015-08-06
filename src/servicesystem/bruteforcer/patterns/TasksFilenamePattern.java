package servicesystem.bruteforcer.patterns;

/**
 * Created by Spider on 01.03.2015.
 */
public class TasksFilenamePattern extends FilenamePattern {
    public TasksFilenamePattern(Object... data) {
        super(data);
        type = "tasks";
        args = new String[]{"u", "t"};
    }
}
