package org.whispersystems.textsecuregcm.auth;

import org.whispersystems.dropwizard.simpleauth.AuthFilter;
import org.whispersystems.dropwizard.simpleauth.AuthSecurityContext;
import org.whispersystems.dropwizard.simpleauth.Authenticator;
import org.whispersystems.textsecuregcm.auth.AuthToken;
import com.google.common.base.Optional;
import com.google.common.io.BaseEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Priority;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import io.dropwizard.auth.AuthenticationException;


@Priority(Priorities.AUTHENTICATION)
public class TokenAuthFilter<P> extends AuthFilter<AuthToken, P> {

  private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthFilter.class);

  private TokenAuthFilter() {}

  @Override
  public void filter(final ContainerRequestContext requestContext) throws IOException {
    final String header = requestContext.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
    try {
      if (header != null) {
        final int space = header.indexOf(' ');
        if (space > 0) {
          final String method = header.substring(0, space);
          if ("Token".equalsIgnoreCase(method)) {
            final AuthToken token = new AuthToken(header.substring(space + 1));
            try {
              Optional<P> principal = authenticator.authenticate(token);
              if (principal.isPresent()) {
                requestContext.setSecurityContext(new AuthSecurityContext<P>(principal.get(), false));
                return;
              }
            } catch (AuthenticationException e) {
              LOGGER.warn("Error authenticating auth token", e);
              throw new InternalServerErrorException();
            }
          }
        }
      }
    } catch (IllegalArgumentException e) {
      LOGGER.warn("Error decoding auth token", e);
    }
    throw new WebApplicationException(unauthorizedHandler.buildResponse(prefix, "TokenAuth"));
  }

  public static class Builder<P> extends AuthFilter.AuthFilterBuilder<AuthToken, P, TokenAuthFilter<P>> {

    @Override
    protected TokenAuthFilter<P> newInstance() {
      return new TokenAuthFilter<>();
    }
  }
}
