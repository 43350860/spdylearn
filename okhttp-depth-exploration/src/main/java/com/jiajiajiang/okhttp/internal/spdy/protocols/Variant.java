package com.jiajiajiang.okhttp.internal.spdy.protocols;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 框架式套接字协议的版本和方言。
 *
 * @author jm
 */
public interface Variant {


    FrameReader newReader(InputStream in, boolean client);

    FrameWriter newWriter(OutputStream out, boolean client);

}
