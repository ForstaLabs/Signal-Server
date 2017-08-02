package org.whispersystems.textsecuregcm.auth;

import com.google.common.base.Optional;
import org.whispersystems.dropwizard.simpleauth.Authenticator;
import org.whispersystems.textsecuregcm.configuration.PartnerConfiguration;
import org.whispersystems.textsecuregcm.partner.Partner;

import java.util.List;

import io.dropwizard.auth.AuthenticationException;


public class PartnerAuthenticator implements Authenticator<AuthToken, Partner> {

  private final List<Partner> partners;

  public PartnerAuthenticator(PartnerConfiguration config) {
    this.partners = config.getPartners();
  }

  public Optional<Partner> authenticate(AuthToken token) throws AuthenticationException {
    if (partners == null) {
      return Optional.absent();
    }
    for (Partner partner : partners) {
      if (token.getToken().equals(partner.getToken())) {
        return Optional.of(partner);
      }
    }
    return Optional.absent();
  }
}
