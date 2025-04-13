package com.NotasBack.NotasFacil.security;

import com.NotasBack.NotasFacil.model.Aluno;
import com.NotasBack.NotasFacil.model.Professor;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.security.AlgorithmParameters;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

@Service
public class TokenService {

    private final Set<String> revokedTokens = new HashSet<>();

    private Instant generateTokenExpiration() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }

    private String secret = "1234";

    public String gerarTokenProfessor(Professor professor) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("notas faceis!")
                    .withSubject(professor.getEmail())
                    .withExpiresAt(this.generateTokenExpiration())
                    .sign(algorithm);
        } catch(JWTCreationException exception) {
            throw new RuntimeException("erro ao criar o token ",exception);
        }
    }

    public String gerarToken(Aluno aluno){
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("notas faceis")
                    .withSubject(aluno.getEmail())
                    .withExpiresAt(this.generateTokenExpiration())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException("erro ao criar o token ",exception);
        }
    }

    public String validarToken(String token) {
        try {
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
            }

            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("notas faceis!")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

    public void revokeToken(String token) {
        revokedTokens.add(token);
    }

    public boolean isTokenRevoked(String token) {
        return revokedTokens.contains(token);
    }
}
