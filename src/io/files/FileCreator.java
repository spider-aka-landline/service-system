package io.files;

import io.IO;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Spider on 17.01.2015.
 */
public class FileCreator {

    public File createFile(String filepath) {
        try {
            if (filepath.contains("\\")) {
                filepath = filepath.replace("\\", "/");
            }

            createDirectory(getDirectoryFromPath(filepath));

            File f = new File(filepath);
            if (!f.exists()) {
                if (!f.createNewFile()) {
                    throw new IOException("Cannot create file.");
                }
            }
            return f;
        } catch (IOException ex) {
            Logger.getLogger(IO.class.getName()).log(Level.SEVERE, filepath, ex);
            return null;
        }

    }



    //гланды автогеном :(
    private String getDirectoryFromPath(String filepath) {
        String[] sa = filepath.split("/");
        StringBuilder dir = new StringBuilder();
        for (int i = 0; i < sa.length - 1; i++) {
            dir.append(sa[i]).append("/");
        }
        return dir.toString();
    }

    private void createDirectory(String dirPath) throws IOException {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                throw new IOException("Cannot create directory.");
            }
        }
    }


}
