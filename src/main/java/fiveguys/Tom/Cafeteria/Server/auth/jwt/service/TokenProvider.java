package fiveguys.Tom.Cafeteria.Server.auth.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

public interface TokenProvider {
    public Jws<Claims> verifyToken(String token);
}
