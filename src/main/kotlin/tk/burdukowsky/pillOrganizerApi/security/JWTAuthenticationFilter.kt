package tk.burdukowsky.pillOrganizerApi.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.io.IOException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.CLAIM_ROLES
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.EXPIRATION_TIME
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.HEADER_STRING
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.SECRET
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.TOKEN_PREFIX
import tk.burdukowsky.pillOrganizerApi.user.AppUser
import java.util.*
import javax.servlet.FilterChain
import java.util.stream.Collectors

@Component
class JWTAuthenticationFilter(authManager: AuthenticationManager) : UsernamePasswordAuthenticationFilter() {

    init {
        authenticationManager = authManager
    }

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val creds = ObjectMapper()
                    .readValue(request.inputStream, AppUser::class.java)

            return authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(
                            creds.username,
                            creds.password,
                            ArrayList<GrantedAuthority>())
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          chain: FilterChain,
                                          authResult: Authentication) {
        val user = authResult.principal as User

        val token = JWT.create()
                .withClaim(CLAIM_ROLES, user.authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.joining(",")))
                .withSubject(user.username)
                .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.toByteArray()))

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token)
    }
}
