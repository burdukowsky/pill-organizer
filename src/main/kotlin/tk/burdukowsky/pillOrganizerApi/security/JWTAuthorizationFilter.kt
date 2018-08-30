package tk.burdukowsky.pillOrganizerApi.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm.HMAC512
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.authentication.AuthenticationManager
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.HEADER_STRING
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.TOKEN_PREFIX
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.CLAIM_ROLES
import tk.burdukowsky.pillOrganizerApi.security.SecurityConstants.SECRET

class JWTAuthorizationFilter(authManager: AuthenticationManager) : BasicAuthenticationFilter(authManager) {

    private fun getAuthentication(request: HttpServletRequest): UsernamePasswordAuthenticationToken? {
        val token = request.getHeader(HEADER_STRING)
        if (token != null) {

            val decodedJWT = JWT.require(HMAC512(SECRET.toByteArray()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))

            val user = decodedJWT.subject

            val authorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(decodedJWT.getClaim(CLAIM_ROLES).asString())

            return if (user != null) {
                UsernamePasswordAuthenticationToken(user, null, authorities)
            } else null
        }
        return null
    }

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        val header = request.getHeader(HEADER_STRING)

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response)
            return
        }

        val authentication = getAuthentication(request)

        SecurityContextHolder.getContext().authentication = authentication
        chain.doFilter(request, response)
    }
}
