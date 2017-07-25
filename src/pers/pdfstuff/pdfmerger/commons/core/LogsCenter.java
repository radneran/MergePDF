package pers.pdfstuff.pdfmerger.commons.core;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import pers.pdfstuff.pdfmerger.commons.events.BaseEvent;

public class LogsCenter {

    /**
     * Filehandler will write to file specified by {@code LOG_FILE} every time
     * there is a new log. It will append to an existing file if possible until
     * 5MB limit. It will create another file once the limit is reached. Max
     * number of log files is limited by MAX_FILE_COUNT.
     */
    private static final int MAX_FILE_COUNT = 4;
    private static final int MAX_FILE_SIZE = (int) Math.pow(2, 20) * 5; //5MB file limit
    private static final String LOG_FILE = "mergepdf.log";
    private static Level logLevel = Level.INFO;
    private static FileHandler fileHandler;
    private static ConsoleHandler consoleHandler;

    private static Logger getLogger(String name) {
        Logger logger = Logger.getLogger(name);
        logger.setUseParentHandlers(false);
        removeHandlers(logger);
        
        addConsoleHandler(logger);
        addFileHandler(logger);
        return logger;
    }

    private static void removeHandlers(Logger logger) {
        Handler[] handlers = logger.getHandlers();
        for (Handler h : handlers) {
            logger.removeHandler(h);
        }
    }

    public static <T> Logger getLogger(Class<T> class1) {
        if (class1 == null) {
            return getLogger("");
        }
        return getLogger(class1.getSimpleName());
    }

    public static void addConsoleHandler(Logger logger) {
        if (consoleHandler == null) {
            createConsoleHandler();
        }
        logger.addHandler(consoleHandler);
    }

    public static void addFileHandler(Logger logger) {
        if (fileHandler == null) {
            try {
                createFileHandler();
            } catch (SecurityException | IOException e) {
                logger.warning("Error adding file handler to logger.");
            }
        }
        logger.addHandler(fileHandler);
    }

    private static void createConsoleHandler() {
        consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(logLevel);
    }

    private static void createFileHandler() throws SecurityException, IOException {
        fileHandler = new FileHandler(LOG_FILE, MAX_FILE_SIZE, MAX_FILE_COUNT, true);
        fileHandler.setFormatter(new SimpleFormatter());
        fileHandler.setLevel(logLevel);
    }

    /**
     * Decorates the given string to create a log message suitable for logging
     * event handling methods.
     */
    public static String getEventHandlingLogMessage(BaseEvent e, String message) {
        return "---[Event handled][" + e + "]" + message;
    }

    /**
     * @see #getEventHandlingLogMessage(BaseEvent, String)
     */
    public static String getEventHandlingLogMessage(BaseEvent e) {
        return getEventHandlingLogMessage(e, "");
    }

}
