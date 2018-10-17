package tk.burdukowsky.pillOrganizerApi.account

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import tk.burdukowsky.pillOrganizerApi.user.AppUserRepository

@RestController
@RequestMapping("account")
class AccountController(private val appUserRepository: AppUserRepository,
                        private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @PatchMapping
    fun updateAccount(@RequestBody jsonBody: JsonNode, authentication: Authentication): ResponseEntity<Void> {
        if (!jsonBody.has("password")) {
            return ResponseEntity.badRequest().build()
        }
        val newPassword = jsonBody.get("password").textValue()

        val currentUser = appUserRepository.findByUsername(authentication.principal as String)
                ?: return ResponseEntity.notFound().build()

        currentUser.password = bCryptPasswordEncoder.encode(newPassword)
        appUserRepository.save(currentUser)

        return ResponseEntity.ok().build()
    }
}
