package org.whispersystems.textsecuregcm.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.annotations.VisibleForTesting;

public class DeviceResponse {

  @JsonProperty
  private long deviceId;

  @JsonProperty
  private String password;

  @VisibleForTesting
  public DeviceResponse() {}

  public DeviceResponse(long deviceId) {
    this.deviceId = deviceId;
  }

  public DeviceResponse(long deviceId, String password) {
    this.deviceId = deviceId;
    this.password = password;
  }

  public long getDeviceId() {
    return deviceId;
  }

  public String getPassword() {
    return password;
  }
}
