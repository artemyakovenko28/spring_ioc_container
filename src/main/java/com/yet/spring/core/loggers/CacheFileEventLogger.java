package com.yet.spring.core.loggers;

import com.yet.spring.core.beans.Event;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CacheFileEventLogger extends FileEventLogger {

    private int cacheSize;
    private List<Event> cache;

    public CacheFileEventLogger(String fileName, int cacheSize) {
        super(fileName);
        this.cacheSize = cacheSize;
        this.cache = new ArrayList<>(cacheSize);
    }

    private void destroy() {
        if (!cache.isEmpty()) {
            writeEventsFromCache();
        }
    }

    @Override
    public void logEvent(Event event) {
        cache.add(event);

        if (cache.size() == cacheSize) {
            writeEventsFromCache();
            cache.clear();
        }
    }

    private void writeEventsFromCache() {
        StringBuilder cacheElements = new StringBuilder();
        cache.forEach(event -> cacheElements.append(event).append("\n"));
        try {
            FileUtils.writeStringToFile(getFile(), cacheElements.toString(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
