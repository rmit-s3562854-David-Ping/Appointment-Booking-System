package main;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;
import java.util.logging.Logger;
/**
 * Created by luke on 2/04/2017.
 */
public class myLogger {

    private static FileHandler fh;



    public static void setup() throws IOException{

        Logger logger = Logger.getLogger("MyLog");

        try {

            // This block configure the logger with handler and formatter
            fh = new FileHandler("log.txt");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
            // the following statement is used to log any messages

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public static void closeHandler(){
        fh.close();
    }


}
