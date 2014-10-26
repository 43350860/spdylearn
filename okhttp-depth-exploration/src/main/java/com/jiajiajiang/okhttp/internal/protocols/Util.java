package com.jiajiajiang.okhttp.internal.protocols;

import java.nio.charset.Charset;
import java.util.concurrent.ThreadFactory;

/**
 * 各种工具方法的集合地
 *
 * @author jm
 */
public class Util {


    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public static ThreadFactory daemonThreadFactory(final String name) {
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread result = new Thread(r, name);
                result.setDaemon(true);
                return result;
            }
        };
        return threadFactory;
    }

}
