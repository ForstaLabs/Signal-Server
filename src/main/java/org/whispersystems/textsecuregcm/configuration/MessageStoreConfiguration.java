package org.whispersystems.textsecuregcm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

import org.hibernate.validator.constraints.NotEmpty;

public class MessageStoreConfiguration {
  @JsonProperty
  @NotEmpty
  private String url;

  public String getUrl() {
    return url;
  }

  public MessageStoreConfiguration() {

  }

  public MessageStoreConfiguration(
    String url)
  {
      this.url = url;
  }
}
