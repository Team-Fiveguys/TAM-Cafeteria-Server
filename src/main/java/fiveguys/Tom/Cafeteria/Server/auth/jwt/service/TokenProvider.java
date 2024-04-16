package fiveguys.Tom.Cafeteria.Server.auth.jwt.service;

public interface TokenProvider {
    public boolean verifyToken(String token);
}
