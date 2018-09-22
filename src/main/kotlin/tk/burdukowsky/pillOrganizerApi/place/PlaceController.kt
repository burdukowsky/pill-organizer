package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("places")
class PlaceController(private val placeRepository: PlaceRepository) {

    @GetMapping("")
    fun getPlaces(): ResponseEntity<List<Place>> {
        return ResponseEntity.ok().body(placeRepository.findAll())
    }

    @DeleteMapping("/{placeId}/pills/{pillId}")
    fun deletePillPlace(@PathVariable(value = "placeId") placeId: Long,
                        @PathVariable(value = "pillId") pillId: Long): ResponseEntity<Void> {
        this.placeRepository.deletePillPlace(pillId, placeId)
        return ResponseEntity.ok().build()
    }

    @PostMapping("")
    fun createPlace(@Valid @RequestBody place: Place): ResponseEntity<Place> {
        val createdPlace = this.placeRepository.save(place)
        return ResponseEntity.ok().body(createdPlace)
    }
}
