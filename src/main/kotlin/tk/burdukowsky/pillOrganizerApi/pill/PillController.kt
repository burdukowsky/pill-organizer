package tk.burdukowsky.pillOrganizerApi.pill

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("pills")
class PillController(private val pillRepository: PillRepository) {

    @GetMapping("")
    fun getPills(): ResponseEntity<List<Pill>> {
        return ResponseEntity.ok().body(pillRepository.findAll())
    }
}
