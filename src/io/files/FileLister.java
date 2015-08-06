package io.files;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;


public class FileLister {

    public Collection<File> getAllFilesInDirectory(File dir) {
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File filename) {
                return filename.isFile();
            }
        };

        return getFilteredFilesInDirectory(dir, fileFilter);
    }

    public Collection<File> getFilteredFilesInDirectory(File dir, FileFilter fileFilter) {
        Collection<File> filteredFiles = new ArrayList<>();
        for (File file : dir.listFiles(fileFilter)) {
            filteredFiles.add(file);
        }
        return filteredFiles;
    }

}
