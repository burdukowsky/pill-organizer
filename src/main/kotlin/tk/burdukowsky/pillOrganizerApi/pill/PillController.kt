package tk.burdukowsky.pillOrganizerApi.pill

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("pills")
class PillController(private val pillRepository: PillRepository) {

    @GetMapping("")
    fun getPills(): ResponseEntity<List<Pill>> {
        return ResponseEntity.ok().body(pillRepository.findAll())
    }

    @PostMapping("")
    fun createPill(@Valid @RequestBody pill: Pill): ResponseEntity<Pill> {
        pill.id = 0
        val createdPill = this.pillRepository.save(pill)
        return ResponseEntity.ok().body(createdPill)
    }
}
