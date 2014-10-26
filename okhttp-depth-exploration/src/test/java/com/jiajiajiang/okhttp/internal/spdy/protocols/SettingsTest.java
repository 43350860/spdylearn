package com.jiajiajiang.okhttp.internal.spdy.protocols;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;


public class SettingsTest{
  
  @Test
  public void isSet() {
    Settings settings = new Settings();
    
    Assert.assertFalse(settings.isSet(Settings.ROUND_TRIP_TIME));
    settings.set(Settings.INITIAL_WINDOW_SIZE, Settings.PERSIST_VALUE, 0);
    Assert.assertTrue(settings.isSet(Settings.ROUND_TRIP_TIME));
  }
  
  @Ignore
  public void unsetField() {
    Settings settings = new Settings();
    Assert.assertEquals(-3, settings.getUploadBandwidth(-3));;
  }
  
  @Test
  @Ignore
  public void setFields() {
    Settings settings = new Settings();
    Assert.assertEquals(-3, settings.getUploadBandwidth(-3));
    settings.set(Settings.UPLOAD_BANDWIDTH, 0, 42);
    Assert.assertEquals(42, settings.getUploadBandwidth(-3));
  }
  
  @Test
  public void isPersisted() {
    Settings settings = new Settings();
    Assert.assertFalse(settings.isPersisted(Settings.ROUND_TRIP_TIME));
    settings.set(Settings.ROUND_TRIP_TIME,Settings.PERSISTED , 0);
    Assert.assertTrue(settings.isPersisted(Settings.ROUND_TRIP_TIME));
  }
  
}
