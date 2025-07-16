package Utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {
    private static String currentRunFolder;
    private static final ThreadLocal<String> currentTestLog = new ThreadLocal<>();

    public static synchronized void initRunFolder() {
        if (currentRunFolder == null) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            currentRunFolder = "Logs-" + timestamp;
            File dir = new File(System.getProperty("user.dir") + "/Reports/" + currentRunFolder);
            dir.mkdirs();
        }
    }

    public static String getRunFolder() {
        if (currentRunFolder == null) {
            initRunFolder();
        }
        return currentRunFolder;
    }

    public static void setCurrentTestLog(String path) {
        currentTestLog.set(path);
    }

    public static String getCurrentTestLog() {
        return currentTestLog.get();
    }

    public static void clearCurrentTestLog() {
        currentTestLog.remove();
    }
}
