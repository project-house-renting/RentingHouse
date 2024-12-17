package fact.it.apigateway.config;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

import java.util.List;

public class WhitelistAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
    private final List<String> allowedEmails = List.of(
            "sanderjannes1@gmail.com"
    );

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMap(auth -> {
                    if (auth.getPrincipal() instanceof Jwt jwt) {
                        String email = jwt.getClaimAsString("email");
                        boolean isAllowed = allowedEmails.contains(email);
                        return Mono.just(new AuthorizationDecision(isAllowed));
                    }
                    return Mono.just(new AuthorizationDecision(false));
                })
                .defaultIfEmpty(new AuthorizationDecision(false));
    }
}
