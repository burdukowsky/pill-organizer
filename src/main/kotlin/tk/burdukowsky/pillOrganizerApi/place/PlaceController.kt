package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import tk.burdukowsky.pillOrganizerApi.pill.Pill
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
        place.id = 0
        val createdPlace = this.placeRepository.save(place)
        return ResponseEntity.ok().body(createdPlace)
    }

    @PostMapping("/{placeId}/pills")
    fun createPillPlace(@PathVariable(value = "placeId") placeId: Long,
                        @Valid @RequestBody pill: Pill): ResponseEntity<Place> {
        val placeOptional = this.placeRepository.findById(placeId)
        if (!placeOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }
        val place = placeOptional.get()
        place.pills.add(pill)
        val updatedPlace = this.placeRepository.save(place)
        return ResponseEntity.ok().body(updatedPlace)
    }
}
