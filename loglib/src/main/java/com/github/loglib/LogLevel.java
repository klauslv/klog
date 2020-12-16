package com.github.loglib;

/**
 * Created by lvming on 12/14/20 7:06 PM.
 * Email: lvming@guazi.com
 * Description:日志级别
 */
public class LogLevel {

    /**
     * Log level for XLog.v.
     */
    public static final int VERBOSE = 2;

    /**
     * Log level for XLog.d.
     */
    public static final int DEBUG = 3;

    /**
     * Log level for XLog.i.
     */
    public static final int INFO = 4;

    /**
     * Log level for XLog.w.
     */
    public static final int WARN = 5;

    /**
     * Log level for XLog.e.
     */
    public static final int ERROR = 6;

    /**
     * Log level for XLog#init, printing all logs.
     */
    public static final int ALL = Integer.MIN_VALUE;

    /**
     * Log level for XLog#init, printing no log.
     */
    public static final int NONE = Integer.MAX_VALUE;

    /**
     * Get a name representing the specified log level.
     *
     * @param logLevel the log level to get name for
     * @return the name
     */
    public static String getLevelName(int logLevel) {
        String levelName;
        switch (logLevel) {
            case VERBOSE:
                levelName = "VERBOSE";
                break;
            case DEBUG:
                levelName = "DEBUG";
                break;
            case INFO:
                levelName = "INFO";
                break;
            case WARN:
                levelName = "WARN";
                break;
            case ERROR:
                levelName = "ERROR";
                break;
            default:
                if (logLevel < VERBOSE) {
                    levelName = "VERBOSE-" + (VERBOSE - logLevel);
                } else {
                    levelName = "ERROR+" + (logLevel - ERROR);
                }
                break;
        }
        return levelName;
    }

    /**
     * Get a short name representing the specified log level.
     *
     * @param logLevel the log level to get short name for
     * @return the short name
     */
    public static String getShortLevelName(int logLevel) {
        String levelName;
        switch (logLevel) {
            case VERBOSE:
                levelName = "V";
                break;
            case DEBUG:
                levelName = "D";
                break;
            case INFO:
                levelName = "I";
                break;
            case WARN:
                levelName = "W";
                break;
            case ERROR:
                levelName = "E";
                break;
            default:
                if (logLevel < VERBOSE) {
                    levelName = "V-" + (VERBOSE - logLevel);
                } else {
                    levelName = "E+" + (logLevel - ERROR);
                }
                break;
        }
        return levelName;
    }
}
