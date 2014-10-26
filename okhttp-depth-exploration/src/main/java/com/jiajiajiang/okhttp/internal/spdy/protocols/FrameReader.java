package com.jiajiajiang.okhttp.internal.spdy.protocols;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * SPDY/3 或者 HTTP 2.0协议的帧读取接口 
 * @author jm
 */
public interface FrameReader extends Closeable {
  
  boolean nextFrame(Handler handler) throws IOException;
  
  public interface Handler {
    
    /**
     * 读取数据帧
     * @param inFinished
     * @param streamId
     * @param in
     * @param length
     * @throws IOException
     */
    void data(boolean inFinished, int streamId, InputStream in,int length) throws IOException ;
    
    /**
     * 读取synStream帧
     * @param inFinished
     * @param streamId
     * @param associatedStreamId
     * @param priority
     * @param nameValueBlock
     * @throws IOException
     */
    void synStream(boolean inFinished,int streamId,int associatedStreamId,int priority, List<String> nameValueBlock);
    
    /**
     * 读取synReply帧
     * @param inFinished
     * @param streamId
     * @param nameAndBlock
     * @throws IOException
     */
    void synReply(boolean inFinished,int streamId,List<String> nameValueBlock) throws IOException;
    
    
    /**
     * 读取headers帧
     * @param streamId
     * @param nameValueBlock
     */
    void headers(int streamId,List<String> nameValueBlock) throws IOException;
    
    /**
     * 读取rstStream帧
     * @param streamId
     * @param errorCode
     */
    void rstStream(int streamId,ErrorCode errorCode);
    
    void settings(boolean clearPrevious,Settings settings);
    
    void noop();
    
    void ping(boolean reply,int payload1,int payload2);
    
    void goAway(int lastGoodStreamId,Error errorCode);
    
    void windowUpdate(int streamId, int deltaWindowSize,boolean endFlowControl) ;
    
    void priority(int streamId,int priority);
  }
}
