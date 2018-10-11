package tk.burdukowsky.pillOrganizerApi.user

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
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
    fun replaceUser(@PathVariable(value = "userId") userId: Long,
                    @Valid @RequestBody user: AppUser): ResponseEntity<AppUser> {
        if (!this.appUserRepository.existsById(userId)) {
            return ResponseEntity.notFound().build()
        }
        user.id = userId
        user.password = bCryptPasswordEncoder.encode(user.password)
        val replacedUser = this.appUserRepository.save(user)
        return ResponseEntity.ok().body(replacedUser)
    }

    @PatchMapping("/{userId}")
    fun updateUser(@PathVariable(value = "userId") userId: Long,
                   @RequestBody jsonBody: JsonNode): ResponseEntity<AppUser> {
        val storedUserOptional = this.appUserRepository.findById(userId)
        if (!storedUserOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }
        val storedUser = storedUserOptional.get()
        if (jsonBody.has("username")) {
            storedUser.username = jsonBody.get("username").textValue()
        }
        if (jsonBody.has("password")) {
            storedUser.password = bCryptPasswordEncoder.encode(jsonBody.get("password").textValue())
        }
        if (jsonBody.has("roles")) {
            val objectMapper = ObjectMapper()
            storedUser.roles = objectMapper.readValue(
                    jsonBody.get("roles").toString(),
                    objectMapper.typeFactory.constructCollectionType(Set::class.java, Role::class.java)
            )
        }
        val updatedUser = this.appUserRepository.save(storedUser)
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
