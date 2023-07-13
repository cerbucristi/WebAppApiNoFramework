package authorization;

public class AuthorizationToken {
    String tokenJwt;

    public AuthorizationToken() {
    }

    public AuthorizationToken(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }

    public String getTokenJwt() {
        return tokenJwt;
    }

    public void setTokenJwt(String tokenJwt) {
        this.tokenJwt = tokenJwt;
    }
}
