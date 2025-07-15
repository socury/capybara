package authtemplate.infrastructure.security.token.properties

import io.jsonwebtoken.Jwts
import org.springframework.boot.context.properties.ConfigurationProperties
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@ConfigurationProperties(prefix = "jwt")
data class TokenProperties(
    val access: Long,
    val refresh: Long,
    private val secret: String
) {
    fun secretKey(): SecretKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), Jwts.SIG.HS512.key().build().algorithm)
}