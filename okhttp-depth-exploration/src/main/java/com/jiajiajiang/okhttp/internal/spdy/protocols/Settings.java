package com.jiajiajiang.okhttp.internal.spdy.protocols;

/**
 * settings帧模型
 * 
 * @author jm
 */
final class Settings {

  /**
   * SPDY/3 规定，所有流的初始化窗口大小默认为64KiB。 (Chrome 25使用的是10MiB)
   */
  static final int DEFAULT_INITIAL_WINDOW_SIZE = 64 << 10;

  /**
   * 标识：对端请求清除持久化的设置
   */
  static final int FLAG_CLEAR_PREVIOUSLY_PERSISTED_SETTINGS = 0x1;

  /**
   * 只有服务端发送该请求。对端请求持久化此设置用于将来的连接
   */
  static final int PERSIST_VALUE = 0x1;
  /**
   * 只有客户端发送该请求。客户端提醒服务端持久化该值
   */
  static final int PERSISTED = 0x2;


  /**
   * 发送者估计的最大传入带宽（单位: kbps）
   */
  static final int UPLOAD_BANDWIDTH = 1;
  /**
   * 发送者估计的最大传出带宽（单位: kbps）
   */
  static final int DOWNLOAD_BANDWIDTH = 2;
  /**
   * 发送者估计的发送请求到收到响应之间的毫秒
   */
  static final int ROUND_TRIP_TIME = 3;
  /**
   * 发送者估计的最大并发流的数量
   */
  static final int MAX_CONCURRENT_STREAMS = 4;
  /**
   * 当前是一个CWND（拥塞窗口）数据包
   */
  static final int CURRENT_CWND = 5;
  /**
   * 下载重传率（百分比）
   */
  static final int DOWNLOAD_RETRANS_RATE = 6;
  /**
   * 窗口大小（单位byte）
   */
  static final int INITIAL_WINDOW_SIZE = 7;
  /**
   * 客户端证书集合的大小
   */
  static final int CLIENT_CERTIFICATE_VECTOR_SIZE = 8;
  /**
   * 流量控制选项
   */
  static final int FLOW_CONTROL_OPTIONS = 9;
  /**
   * settings的总数量
   */
  static final int COUNT = 10;
  /**
   * 如果设置，则流量控制将被屏蔽
   */
  static final int FLOW_CONTROL_OPTIONS_DISABLED = 0x1;

  /**
   * 比特位字段标识该值
   */
  private int set;

  /**
   * {@link #PERSIST_VALUE}. 标识的bit位字段
   */
  private int persistValue;
  
  /**
   * {@link #PERSISTED} 标识的bit位字段
   */
  private int persisted;

  /**
   * 标识值
   */
  private final int values[] = new int[COUNT];

  void set(int id, int idFlags, int value) {
    if (id >= values.length) {
      return;// 未知的设置项
    }
    int bit = 1 << id;
    set |= bit;
    if ((idFlags & PERSIST_VALUE) != 0) {
      persistValue |= bit;
    } else {
      persistValue &= ~bit;
    }
    
    if ((idFlags & PERSISTED) != 0) {
      persisted |= bit;
    } else {
      persisted &= ~bit;
    }
    
    values[id] = value;
  }

  boolean isSet(int id) {
    int bit = 1 << id;
    return (set & bit) != 0;
  }
  
  int get(int id) {
    return values[id];
  }

  int flags(int id) {
    int result = 0;
    if (isPersisted(id)) {
      result |= Settings.PERSISTED;
    }
    if (persistValue(id)) {
      return result |= Settings.PERSIST_VALUE;
    }
    return result;
  }
  
  int size() {
    return Integer.bitCount(set);
  }
  
  int getUploadBandwidth(int defaultValue) {
    int bit = 1 << UPLOAD_BANDWIDTH;
    return (bit & set) != 0?values[UPLOAD_BANDWIDTH] : defaultValue;
  }
  
  int getDownloadBandwidth(int defaultValue) {
    int bit = 1 << DOWNLOAD_BANDWIDTH;
    return (bit & set) != 0?values[DOWNLOAD_BANDWIDTH]:defaultValue;
  }
  
  int getMaxConcurrentStreams(int defaultValue) {
    int bit = 1 << MAX_CONCURRENT_STREAMS;
    return (bit & set) != 0 ?values[MAX_CONCURRENT_STREAMS]:defaultValue; 
  }
  
  int getCurrentCwnd(int defaultValue) {
    int bit = 1<< CURRENT_CWND;
    return (bit & set) != 0 ? values[CURRENT_CWND]:defaultValue;
  }
  
  int getDownloadRetransRate(int defaultValue) {
    if (isSet(DOWNLOAD_RETRANS_RATE)) {
      return values[DOWNLOAD_RETRANS_RATE];
    } else {
      return defaultValue;
    }
  }
  
  int getInitialWindowSize(int defaultValue) {
    return isSet(INITIAL_WINDOW_SIZE)?values[INITIAL_WINDOW_SIZE]:defaultValue;
  }
  
  int getClientCertificateSize(int defaultValue) {
    return getValue(CLIENT_CERTIFICATE_VECTOR_SIZE, defaultValue);
  }
  
  boolean isFlowControlDisabled () {
    int flowControlOptions = getValue(FLOW_CONTROL_OPTIONS, 0);
    return (flowControlOptions & FLOW_CONTROL_OPTIONS_DISABLED) != 0;
  }
  
  /**
   * 如果返回true，用户代理应该在未来的SPDY连接中使用该设置去连接相同的主机。
   * @param id
   * @return
   */
  boolean persistValue(int id) {
    int bit = 1<<id;
    return (persistValue & bit) != 0;
  }
  
  boolean isPersisted(int id) {
    int bit = 1 << id;
    return (persisted & bit) != 0;
  }
  
  void merge(Settings other) {
    for (int i = 0; i < COUNT; i++) {
      if (!other.isSet(i)) {
        continue;
      }
      set(i, other.flags(i), other.get(i));
    }
  }
  
  int getValue(int id,int defaultValue) {
    return isSet(id)?values[id]:defaultValue;
  }
  
  public static void main(String[] args) {
    for (int i = 1; i <= 9; i++) {
      System.out.println(i+"二进制"+Integer.toBinaryString(i<<2));
      System.out.println(i+"十六进制"+Integer.toHexString(i<<2));
    }
  }
  
}
