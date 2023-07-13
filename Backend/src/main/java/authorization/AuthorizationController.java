package authorization;

import api.AuthorizationApi;
import exceptions.AuthenticationException;
import exceptions.RegisterConflictException;
import handlers.Response;

import java.security.NoSuchAlgorithmException;

public class AuthorizationController implements AuthorizationApi {
    @Override
    public Response register(RegisterRequest registerRequest) throws RegisterConflictException, NoSuchAlgorithmException {
        AuthorizationService.saveUser(registerRequest);
        return Response.created();
    }

    @Override
    public AuthorizationToken authenticate(AuthenticationRequest authenticationRequest) throws AuthenticationException, NoSuchAlgorithmException {
        return AuthorizationService.authenticate(authenticationRequest);
    }
}
