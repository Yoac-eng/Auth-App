package com.yoaceng.authapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.yoaceng.authapp.domain.user.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;


/**
 * Class that creates and validate JWT Tokens.
 *
 * @author Cayo Cutrim
 */
@Service
public class TokenService {
    @Value("${api.security.token.secret}")
    private String secret;

    /**
     * @param user User that will have a token generated for him.
     *  The user's login will be used as the token subject.
     *
     * @return JWT Token
     */
    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("auth-api")
                    .withSubject(user.getLogin()) // Assunto do token, identificador do usuário ou chave
                    .withExpiresAt(genExpirationDate())
                    .sign(algorithm); // Assina o token com algoritmo e chave especificados

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    /**
     * @param token that will be validated.
     *
     * @return String The user's login if the token is valid.
     * The token is invalid if the time ran out, the sign is wrong or the issuer is wrong.
     */
    public String validateToken(String token){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("auth-api")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            // Uma string vazia é retornada pois representa que não tem usuário válido pra esse token
            // Gerando um 403 pelo spring security
            return "";
        }
    }

    /**
     *
     * @return Instant The token expiration time considering the Brazil UTC.
     */
    public Instant genExpirationDate(){
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
