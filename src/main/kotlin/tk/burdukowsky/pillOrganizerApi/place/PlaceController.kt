package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("places")
class PlaceController {

    @GetMapping("")
    fun getPlaces(): ResponseEntity<List<Place>> {
        return ResponseEntity.ok().body(Collections.emptyList())
    }
}
