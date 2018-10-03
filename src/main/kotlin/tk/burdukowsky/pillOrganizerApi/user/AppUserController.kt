package tk.burdukowsky.pillOrganizerApi.user

import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("users")
class AppUserController(private val appUserRepository: AppUserRepository,
                        private val bCryptPasswordEncoder: BCryptPasswordEncoder) {

    @GetMapping("")
    fun getUsers(): ResponseEntity<List<AppUser>> {
        return ResponseEntity.ok().body(appUserRepository.findAll())
    }

    @PostMapping("")
    fun createUser(@Valid @RequestBody user: AppUser): ResponseEntity<AppUser> {
        user.id = 0
        user.password = bCryptPasswordEncoder.encode(user.password)
        val createdUser = this.appUserRepository.save(user)
        return ResponseEntity.ok().body(createdUser)
    }

    @PutMapping("/{userId}")
    fun updateUser(@PathVariable(value = "userId") userId: Long,
                   @Valid @RequestBody user: AppUser): ResponseEntity<AppUser> {
        if (!this.appUserRepository.existsById(userId)) {
            return ResponseEntity.notFound().build()
        }
        user.id = userId
        user.password = bCryptPasswordEncoder.encode(user.password)
        val updatedUser = this.appUserRepository.save(user)
        return ResponseEntity.ok().body(updatedUser)
    }

    @DeleteMapping("/{userId}")
    fun deleteUser(@PathVariable(value = "userId") userId: Long): ResponseEntity<Void> {
        if (!this.appUserRepository.existsById(userId)) {
            return ResponseEntity.notFound().build()
        }
        this.appUserRepository.deleteById(userId)
        return ResponseEntity.ok().build()
    }
}
