package io.files.filters;

import java.io.File;
import java.io.FileFilter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Spider on 01.03.2015.
 */
public class InputDataFileFilter implements FileFilter {

    protected final Pattern pattern;
    protected Matcher matcher;

    public InputDataFileFilter(String futurePattern) {
        pattern = Pattern.compile(futurePattern);
    }

    @Override
    public boolean accept(File file) {
        return (file.isFile() && isFilenameMatchPattern(file.getName()));
    }

    protected boolean isFilenameMatchPattern(String filename){
        matcher = pattern.matcher(filename);
        return matcher.matches();
    }


}
