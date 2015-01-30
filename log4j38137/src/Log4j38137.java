

import org.apache.log4j.AsyncAppender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.MockEvent;
import org.apache.log4j.PatternLayout;

/**
 * Bug URL:https://issues.apache.org/bugzilla/show_bug.cgi?id=38137
 * This is a wait-notify deadlock.
 * Reproduce environment: log4j 1.2.13 ,JDK 1.6.0_33
 * 
 * 
 * @author Ziyi Lin
 */
public class Log4j38137 {

    private AsyncAppender asyncAppender;

    public Log4j38137() {
        asyncAppender = new AsyncAppender();
        configLogger();
    }

    private void configLogger() {
        ConsoleAppender console = new ConsoleAppender(); // create appender
        // configure the appender
        String PATTERN = "[%d{dd-MM-yyyy HH:mm:ss,SSS}][%-5p][%c] %M : %m%n";
        console.setLayout(new PatternLayout(PATTERN));
        console.setThreshold(Level.INFO);
        console.activateOptions();

        LogManager.getRootLogger().addAppender(console);

    }

    public void callAppender() {
        int i = 0;
        int threadNum = 3;
        Thread[] ts = new Thread[threadNum];
        while (i < threadNum) {
            ts[i] = new AppendThread(i);
            ts[i].start();
            i++;
        }
        for (Thread t : ts) {
            try {
                t.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Log4j38137 test = new Log4j38137();
        test.callAppender();
    }

    private class AppendThread extends Thread {

        public AppendThread(int index) {
            super("Appender-" + index);
        }

        public void run() {
            int i = 0;
            // fill up the buffer. when buffer is full,
            // thread waits for other thread to remove elements
            // from buffer and notify it to wake up. But when all
            // threads appended events to buffer to full, then all
            // of them are waiting for notifying.
            while (i < 50) {
                MockEvent event = new MockEvent();
                asyncAppender.append(event);
                i++;
            }
        }
    }

   
}