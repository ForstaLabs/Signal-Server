package org.whispersystems.textsecuregcm.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.whispersystems.textsecuregcm.partner.Partner;

import java.util.List;

public class PartnerConfiguration {

  @JsonProperty
  private List<Partner> partners;

  @JsonProperty
  private String name;

  public List<Partner> getPartners() {
    return partners;
  }

  public String getName() {
    return name;
  }
}
