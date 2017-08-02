package org.whispersystems.textsecuregcm.auth;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class AuthToken {
    private final String token;

    public AuthToken(String token) {
        this.token = requireNonNull(token);
    }

    public String getToken() {
        return token;
    }

    public String getName() {
        return "AuthToken";
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
        final AuthToken other = (AuthToken) obj;
        return Objects.equals(token, other.getToken());
    }

    public String toString() {
        return token.toString();
    }
}
