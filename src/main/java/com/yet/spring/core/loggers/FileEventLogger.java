package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
public class FileEventLogger implements EventLogger {

    private File file;
    private String fileName;

    public FileEventLogger(@Value("target/events_log.txt") String fileName) {
        this.fileName = fileName;
    }

    public File getFile() {
        return file;
    }

    @PostConstruct
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
