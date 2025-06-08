import com.NotasBack.NotasFacil.model.Aluno
import com.NotasBack.NotasFacil.model.Escola
import com.NotasBack.NotasFacil.model.Professor
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTCreationException
import com.auth0.jwt.exceptions.JWTVerificationException
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

class TokenService {

    private val revokedTokens = mutableSetOf<String>()

    private fun generateTokenExpiration(): Instant {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"))
    }

    private val secret = "1234"

    fun gerarTokenProfessor(professor: Professor): String {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.create()
                .withIssuer("notas faceis!")
                .withClaim("role", professor.role)
                .withExpiresAt(generateTokenExpiration())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("erro ao criar o token", exception)
        }
    }

    fun gerarTokenAluno(aluno: Aluno): String {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.create()
                .withIssuer("notas faceis")
                .withSubject(aluno.email)
                .withExpiresAt(generateTokenExpiration())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("erro ao criar o token", exception)
        }
    }

    fun gerarTokenEscola(escola: Escola): String {
        return try {
            val algorithm = Algorithm.HMAC256(secret)
            JWT.create()
                .withIssuer("notas faceis!")
                .withSubject(escola.email)
                .withExpiresAt(generateTokenExpiration())
                .sign(algorithm)
        } catch (exception: JWTCreationException) {
            throw RuntimeException("erro ao criar o token", exception)
        }
    }

    fun validarToken(token: String): String? {
        return try {
            var cleanToken = token
            if (cleanToken.startsWith("Bearer ")) {
                cleanToken = cleanToken.substring(7)
            }
            val algorithm = Algorithm.HMAC256(secret)
            JWT.require(algorithm)
                .withIssuer("notas faceis!")
                .build()
                .verify(cleanToken)
                .subject
        } catch (exception: JWTVerificationException) {
            null
        }
    }

    fun revokeToken(token: String) {
        revokedTokens.add(token)
    }

    fun isTokenRevoked(token: String): Boolean {
        return revokedTokens.contains(token)
    }
}
