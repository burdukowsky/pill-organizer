package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("places")
class PlaceController(private val placeRepository: PlaceRepository) {

    @GetMapping("")
    fun getPlaces(): ResponseEntity<List<Place>> {
        return ResponseEntity.ok().body(placeRepository.findAll())
    }
}
