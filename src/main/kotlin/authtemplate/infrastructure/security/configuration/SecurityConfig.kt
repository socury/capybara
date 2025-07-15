package authtemplate.infrastructure.security.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import authtemplate.infrastructure.security.filter.TokenExceptionHandlerFilter
import authtemplate.infrastructure.security.filter.TokenFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val tokenFilter: TokenFilter,
    private val tokenExceptionHandlerFilter: TokenExceptionHandlerFilter
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .formLogin{ it.disable() }
            .httpBasic{ it.disable() }
            .cors { it.disable() }
            .authorizeHttpRequests{ request ->
                request
                    .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**").permitAll()
                    .requestMatchers("/auth/*").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterAfter(tokenFilter, UsernamePasswordAuthenticationFilter::class.java)
            .addFilterBefore(tokenExceptionHandlerFilter, TokenFilter::class.java)
        return http.build()
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }
}