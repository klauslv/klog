package com.github.sample;

/**
 * Created by lvming on 12/9/20 10:48 AM.
 * Email: lvming@guazi.com
 * Description:
 */
public final class FakeCrashLibrary {
    public static void log(int priority,String tag,String message){
        // TODO add log entry to circular buffer.

    }

    public static void logWarning(Throwable t){
        // TODO report non-fatal warning.
    }

    public static void logError(Throwable t){
        // TODO report non-fatal error.
    }

    private FakeCrashLibrary(){
        throw new AssertionError("no instance");
    }
}
