package io.files.filters;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Spider on 01.03.2015.
 */
public class PresettedDataFileFilter extends InputDataFileFilter {

    private final Collection<String> filenames = new ArrayList<>();

    public PresettedDataFileFilter(String futurePattern) {
        super(futurePattern);
    }

    public PresettedDataFileFilter(String futurePattern, Collection<String> names) {
        this(futurePattern);
        filenames.addAll(names);
    }

    @Override
    public boolean accept(File file) {
        return (super.accept(file)&& inPresettedArray(file.getName()));

    }

    private boolean inPresettedArray(String filename) {
        return filenames.contains(filename);
    }


}
