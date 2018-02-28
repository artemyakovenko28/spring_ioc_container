package com.yet.spring.core.config;

import com.yet.spring.core.App;
import com.yet.spring.core.aspects.LoggingAspect;
import com.yet.spring.core.beans.Client;
import com.yet.spring.core.beans.Event;
import com.yet.spring.core.beans.EventType;
import com.yet.spring.core.loggers.EventLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.util.*;

@Configuration
@ComponentScan(basePackages = "com.yet.spring.core")
@PropertySource("classpath:client.properties")
@EnableAspectJAutoProxy
public class AppConfig {

    @Resource(name = "consoleEventLogger")
    private EventLogger consoleEventLogger;

    @Resource(name = "fileEventLogger")
    private EventLogger fileEventLogger;

    @Resource(name = "cacheFileEventLogger")
    private EventLogger cacheFileEventLogger;

    @Resource(name = "combinedEventLogger")
    private EventLogger combinedEventLogger;

    @Autowired
    private Environment env;

    @Bean
    public Client client() {
        return new Client(env.getProperty("id"),
                env.getProperty("name"),
                env.getProperty("greeting"));
    }

    @Bean
    public App app() {
        Client client = client();

        EventLogger defaultLogger = cacheFileEventLogger;

        Map<EventType, EventLogger> loggerMap = new HashMap<>();
        loggerMap.put(EventType.INFO, consoleEventLogger);
        loggerMap.put(EventType.ERROR, combinedEventLogger);

        return new App(client, defaultLogger, loggerMap);
    }

    @Bean
    @Scope("prototype")
    public Event event() {
        Date currentDate = new Date();
        DateFormat dateFormat = DateFormat.getDateTimeInstance();
        return new Event(currentDate, dateFormat);
    }

    @Bean
    public Collection<EventLogger> combinedLoggers() {
        Collection<EventLogger> loggers = new ArrayList<>();
        loggers.add(consoleEventLogger);
        loggers.add(fileEventLogger);
        return loggers;
    }

//    @Bean
//    public LoggingAspect loggingAspect() {
//        return new LoggingAspect();
//    }
}
