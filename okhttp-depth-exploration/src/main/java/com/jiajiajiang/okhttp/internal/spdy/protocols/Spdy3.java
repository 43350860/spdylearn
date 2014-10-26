package com.jiajiajiang.okhttp.internal.spdy.protocols;

import com.jiajiajiang.okhttp.internal.protocols.Util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by jm on 14-10-27.
 */
public class Spdy3 implements Variant {

    static final int TYPE_DATA = 0x0;
    static final int TYPE_SYN_STREAM = 0x1;
    static final int TYPE_SYN_REPLY = 0x2;
    static final int TYPE_RST_STREAM = 0x3;
    static final int TYPE_SETTINGS = 0x4;
    static final int TYPE_NOOP = 0x5;
    static final int TYPE_PING = 0x6;
    static final int TYPE_GOAWAY = 0x7;
    static final int TYPE_HEADERS = 0x8;
    static final int TYPE_WINDOW_UPDATE = 0x9;
    static final int TYPE_CREDENTIAL = 0x10;


    static final int FLAG_FIN = 0x1;
    static final int FLAG_UNIDIRECTIONAL = 0x2;

    static final int VERSION = 3;

    static final byte DICTIONARY[];

    static {
        try {
            DICTIONARY = ("\u0000\u0000\u0000\u0007options\u0000\u0000\u0000\u0004hea"
                    + "d\u0000\u0000\u0000\u0004post\u0000\u0000\u0000\u0003put\u0000\u0000\u0000\u0006dele"
                    + "te\u0000\u0000\u0000\u0005trace\u0000\u0000\u0000\u0006accept\u0000\u0000\u0000"
                    + "\u000Eaccept-charset\u0000\u0000\u0000\u000Faccept-encoding\u0000\u0000\u0000\u000Fa"
                    + "ccept-language\u0000\u0000\u0000\raccept-ranges\u0000\u0000\u0000\u0003age\u0000"
                    + "\u0000\u0000\u0005allow\u0000\u0000\u0000\rauthorization\u0000\u0000\u0000\rcache-co"
                    + "ntrol\u0000\u0000\u0000\nconnection\u0000\u0000\u0000\fcontent-base\u0000\u0000"
                    + "\u0000\u0010content-encoding\u0000\u0000\u0000\u0010content-language\u0000\u0000"
                    + "\u0000\u000Econtent-length\u0000\u0000\u0000\u0010content-location\u0000\u0000\u0000"
                    + "\u000Bcontent-md5\u0000\u0000\u0000\rcontent-range\u0000\u0000\u0000\fcontent-type"
                    + "\u0000\u0000\u0000\u0004date\u0000\u0000\u0000\u0004etag\u0000\u0000\u0000\u0006expe"
                    + "ct\u0000\u0000\u0000\u0007expires\u0000\u0000\u0000\u0004from\u0000\u0000\u0000"
                    + "\u0004host\u0000\u0000\u0000\bif-match\u0000\u0000\u0000\u0011if-modified-since"
                    + "\u0000\u0000\u0000\rif-none-match\u0000\u0000\u0000\bif-range\u0000\u0000\u0000"
                    + "\u0013if-unmodified-since\u0000\u0000\u0000\rlast-modified\u0000\u0000\u0000\blocati"
                    + "on\u0000\u0000\u0000\fmax-forwards\u0000\u0000\u0000\u0006pragma\u0000\u0000\u0000"
                    + "\u0012proxy-authenticate\u0000\u0000\u0000\u0013proxy-authorization\u0000\u0000"
                    + "\u0000\u0005range\u0000\u0000\u0000\u0007referer\u0000\u0000\u0000\u000Bretry-after"
                    + "\u0000\u0000\u0000\u0006server\u0000\u0000\u0000\u0002te\u0000\u0000\u0000\u0007trai"
                    + "ler\u0000\u0000\u0000\u0011transfer-encoding\u0000\u0000\u0000\u0007upgrade\u0000"
                    + "\u0000\u0000\nuser-agent\u0000\u0000\u0000\u0004vary\u0000\u0000\u0000\u0003via"
                    + "\u0000\u0000\u0000\u0007warning\u0000\u0000\u0000\u0010www-authenticate\u0000\u0000"
                    + "\u0000\u0006method\u0000\u0000\u0000\u0003get\u0000\u0000\u0000\u0006status\u0000"
                    + "\u0000\u0000\u0006200 OK\u0000\u0000\u0000\u0007version\u0000\u0000\u0000\bHTTP/1.1"
                    + "\u0000\u0000\u0000\u0003url\u0000\u0000\u0000\u0006public\u0000\u0000\u0000\nset-coo"
                    + "kie\u0000\u0000\u0000\nkeep-alive\u0000\u0000\u0000\u0006origin100101201202205206300"
                    + "302303304305306307402405406407408409410411412413414415416417502504505203 Non-Authori"
                    + "tative Information204 No Content301 Moved Permanently400 Bad Request401 Unauthorized"
                    + "403 Forbidden404 Not Found500 Internal Server Error501 Not Implemented503 Service Un"
                    + "availableJan Feb Mar Apr May Jun Jul Aug Sept Oct Nov Dec 00:00:00 Mon, Tue, Wed, Th"
                    + "u, Fri, Sat, Sun, GMTchunked,text/html,image/png,image/jpg,image/gif,application/xml"
                    + ",application/xhtml+xml,text/plain,text/javascript,publicprivatemax-age=gzip,deflate,"
                    + "sdchcharset=utf-8charset=iso-8859-1,utf-,*,enq=0.").getBytes(Util.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError();
        }

    }


    @Override
    public FrameReader newReader(InputStream in, boolean client) {
        return null;
    }

    @Override
    public FrameWriter newWriter(OutputStream out, boolean client) {
        return null;
    }

    /**
     * 读取spdy
     */
    static final class Reader implements FrameWriter {

        @Override
        public void connectionHeader() {

        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, int priority, int slot, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void synReply(boolean outFinished, int streamId, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void headers(int streamId, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void rstStream(int streamId, ErrorCode errorCode) throws IOException {

        }

        @Override
        public void data(boolean outFinished, int streamId, byte[] data) throws IOException {

        }

        @Override
        public void data(boolean outFinished, int streamId, byte data, int offset, int byteCount) throws IOException {

        }

        @Override
        public void settings(Settings settings) throws IOException {

        }

        @Override
        public void noop() throws IOException {

        }

        @Override
        public void ping(boolean reply, int payload1, int payload2) throws IOException {

        }

        @Override
        public void goAway(int lastGoodStreamId, ErrorCode errorCode) throws IOException {

        }

        @Override
        public void windowUpdate(int streamId, int daltaWindowSize) throws IOException {

        }

        @Override
        public void close() throws IOException {

        }
    }

    /*=============================================================================================*/

    /**
     * 写 spdy/3 帧
     */
    static final class Writer implements FrameWriter {
        @Override
        public void connectionHeader() {

        }

        @Override
        public void flush() throws IOException {

        }

        @Override
        public void synStream(boolean outFinished, boolean inFinished, int streamId, int associatedStreamId, int priority, int slot, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void synReply(boolean outFinished, int streamId, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void headers(int streamId, List<String> nameValueBlock) throws IOException {

        }

        @Override
        public void rstStream(int streamId, ErrorCode errorCode) throws IOException {

        }

        @Override
        public void data(boolean outFinished, int streamId, byte[] data) throws IOException {

        }

        @Override
        public void data(boolean outFinished, int streamId, byte data, int offset, int byteCount) throws IOException {

        }

        @Override
        public void settings(Settings settings) throws IOException {

        }

        @Override
        public void noop() throws IOException {

        }

        @Override
        public void ping(boolean reply, int payload1, int payload2) throws IOException {

        }

        @Override
        public void goAway(int lastGoodStreamId, ErrorCode errorCode) throws IOException {

        }

        @Override
        public void windowUpdate(int streamId, int daltaWindowSize) throws IOException {

        }

        @Override
        public void close() throws IOException {

        }
    }
}
