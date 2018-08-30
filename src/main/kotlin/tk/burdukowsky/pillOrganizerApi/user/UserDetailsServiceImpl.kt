package tk.burdukowsky.pillOrganizerApi.user

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val appUserRepository: AppUserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val appUser = appUserRepository.findByUsername(username) ?: throw UsernameNotFoundException(username)
        return User(appUser.username, appUser.password, emptyList())
    }
}
