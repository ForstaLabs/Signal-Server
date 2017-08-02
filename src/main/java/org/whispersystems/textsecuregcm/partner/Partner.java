package org.whispersystems.textsecuregcm.partner;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class Partner {

  @NotEmpty
  @JsonProperty
  private String name;

  @NotEmpty
  @JsonProperty
  private String token;

  public Partner() {}

  public Partner(String name, String token) {
    this.name = name;
    this.token = token;
  }

  public String getName() {
    return name;
  }

  public String getToken() {
    return token;
  }

  public int hashCode() {
    return Objects.hash(token);
  }

  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    final Partner other = (Partner) obj;
    return Objects.equals(token, other.getToken());
  }

  public String toString() {
    return token.toString();
  }
}
