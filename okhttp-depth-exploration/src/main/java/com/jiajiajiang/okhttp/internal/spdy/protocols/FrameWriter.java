package com.jiajiajiang.okhttp.internal.spdy.protocols;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * 对应于SPDY3 或者 HTTP/2.0 的写传输帧接口
 *
 * @author jm
 */
public interface FrameWriter extends Closeable {

    void connectionHeader();

    void flush() throws IOException;

    void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, int priority, int slot, List<String> nameValueBlock) throws IOException;

    void synReply(boolean outFinished, int streamId, List<String> nameValueBlock) throws IOException;

    void headers(int streamId,List<String> nameValueBlock) throws IOException;

    void rstStream(int streamId,ErrorCode errorCode) throws IOException;

    void data(boolean outFinished,int streamId, byte data[]) throws IOException;

    void data(boolean outFinished,int streamId, byte data,int offset, int byteCount) throws IOException;

    void settings(Settings settings) throws IOException;

    void noop() throws IOException;

    void ping(boolean reply, int payload1, int payload2) throws IOException;

    void goAway(int lastGoodStreamId, ErrorCode errorCode) throws  IOException;

    void windowUpdate(int streamId, int daltaWindowSize) throws IOException;

}
