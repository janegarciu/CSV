package com.testtask.csv.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogHandler {
    private static Logger logger = Logger.getLogger(LogHandler.class.getName());
    private static FileHandler fileHandler;

    public static Logger createFileHandler() {
        {
            try {
                fileHandler = new FileHandler("C:\\CSV\\src\\main\\resources\\logs\\app.log", true);
                logger.addHandler(fileHandler);
                if (logger.isLoggable(Level.INFO)) {
                    logger.info("Information message");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return logger;
        }
    }
}
