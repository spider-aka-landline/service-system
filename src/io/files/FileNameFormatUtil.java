package io.files;

/**
 * Created by Spider on 17.01.2015.
 */
public class FileNameFormatUtil {

    private static final String RESULTS_FILEPATH = "/results/";
    private String appendix;
    String baseDir = System.getProperty("user.dir");

    public void setAppendix(String s) {
        appendix = s;
    }

    public String getResultsFilePath(String filename) {
        return getAbsoluteFilePath(RESULTS_FILEPATH + filename, true);
    }

    public String getAbsoluteFilePath(String filename) {
        if (checkIfPathAbsolute(filename))
            return filename;
        else return getAbsoluteFilePath(filename, true);
    }

    public String getAbsoluteFilePath(String filename, boolean addNumbers) {
        if (filename == null) {
            throw new NullPointerException("Empty filename");
        }

        StringBuilder filepath = getAbsoluteDirectory(addNumbers);
        filepath.append(filename);
        return filepath.toString();
    }

    private StringBuilder getAbsoluteDirectory(boolean addNumbers) {
        if (baseDir.matches("([\\S]+\\/)")) {
            baseDir.replaceAll("\\/", "\\");
        }
        StringBuilder dir = new StringBuilder(baseDir);
        dir.append(RESULTS_FILEPATH);
        if (addNumbers) {
            dir.append(appendix);
        }
        return dir;
    }

    public String getCurrentDirectoryPath(){
        return getAbsoluteDirectory(true).toString();
    }


    private String getFilenameFromPath(String filepath) {
        String[] sa;
        sa = filepath.split("//");
        return sa[sa.length - 1];
    }

    public boolean checkIfPathAbsolute(String filepath) {
        //if ((filepath.charAt(0) != '/') && (filepath.charAt(1)!=':'))return false; // can't understand, why it isn't working
        if (filepath.length()>0 && filepath.charAt(1) == ':') return true;
        return filepath.contains(baseDir);
    }

}
