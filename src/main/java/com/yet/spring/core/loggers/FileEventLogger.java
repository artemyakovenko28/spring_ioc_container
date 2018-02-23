package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileEventLogger implements EventLogger {

    private File file;
    private String fileName;

    public FileEventLogger(String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    public void init() {
        this.file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new IllegalArgumentException("Can't create file", e);
            }
        } else if (!file.canWrite()) {
            throw new IllegalArgumentException("Can't write to file " + fileName);
        }
    }

    @Override
    public void logEvent(Event event) {
        try {
            FileUtils.writeStringToFile(file, event.toString() + "\n", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
