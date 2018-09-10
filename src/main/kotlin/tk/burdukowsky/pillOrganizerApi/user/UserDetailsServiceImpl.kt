package tk.burdukowsky.pillOrganizerApi.user

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class UserDetailsServiceImpl(private val appUserRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val appUser = appUserRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        val authorities = appUser.roles.stream()
                .map { role -> SimpleGrantedAuthority(role.authority()) }
                .collect(Collectors.toList())
        return User(appUser.username, appUser.password, authorities)
    }
}
