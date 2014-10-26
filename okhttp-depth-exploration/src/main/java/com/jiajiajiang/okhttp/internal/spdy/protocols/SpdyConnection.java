package com.jiajiajiang.okhttp.internal.spdy.protocols;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.jiajiajiang.okhttp.internal.protocols.Util;

/**
 * 主要负责客户端和服务端之间的套接字连接、读写等操作。
 * @author jm
 */
public final class SpdyConnection implements Closeable{
  
  
  private static final ExecutorService executor = 
      new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(),Util.daemonThreadFactory("OkHttp SpdyConnection"));

  

  @Override
  public void close() throws IOException {
    // TODO Auto-generated method stub
    
  }

}
