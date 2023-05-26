package main;

import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {
    private final static Logger logger = Logger.getLogger("MyLog");

    public static void init() {
        FileHandler fh;
        try{
            fh = new FileHandler("MyLogFile.txt");
            fh.setLevel(Level.CONFIG);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setLevel(Level.CONFIG);
            logger.info("Logger Initialized");
        } catch (Exception e) {
            logger.warning("Exception ::" + e);
        }
    }

    public static Logger getMyLogger(){
        return logger;
    }

}
