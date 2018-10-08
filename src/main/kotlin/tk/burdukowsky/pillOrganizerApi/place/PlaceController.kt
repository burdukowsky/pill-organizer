package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import tk.burdukowsky.pillOrganizerApi.pill.Pill
import tk.burdukowsky.pillOrganizerApi.pill.PillRepository
import javax.validation.Valid

@RestController
@RequestMapping("places")
class PlaceController(private val placeRepository: PlaceRepository, private val pillRepository: PillRepository) {

    @GetMapping("")
    fun getPlaces(): ResponseEntity<List<Place>> {
        return ResponseEntity.ok().body(placeRepository.findAll())
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @DeleteMapping("/{placeId}/pills/{pillId}")
    fun deletePillPlace(@PathVariable(value = "placeId") placeId: Long,
                        @PathVariable(value = "pillId") pillId: Long): ResponseEntity<Void> {
        val placeOptional = this.placeRepository.findById(placeId)
        if (!placeOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }
        val place = placeOptional.get()
        place.pills.removeIf { pill -> pill.id == pillId }
        this.placeRepository.save(place)
        return ResponseEntity.ok().build()
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping("")
    fun createPlace(@Valid @RequestBody place: Place): ResponseEntity<Place> {
        place.id = 0
        val createdPlace = this.placeRepository.save(place)
        return ResponseEntity.ok().body(createdPlace)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PostMapping("/{placeId}/pills")
    fun createPillPlace(@PathVariable(value = "placeId") placeId: Long,
                        @Valid @RequestBody pill: Pill): ResponseEntity<Place> {
        val placeOptional = this.placeRepository.findById(placeId)
        if (!placeOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val pillOptional = this.pillRepository.findById(pill.id)
        if (!pillOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }

        val place = placeOptional.get()
        place.pills.add(pillOptional.get())
        val updatedPlace = this.placeRepository.save(place)
        return ResponseEntity.ok().body(updatedPlace)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @PutMapping("/{placeId}")
    fun updatePlace(@PathVariable(value = "placeId") placeId: Long,
                    @Valid @RequestBody place: Place): ResponseEntity<Place> {
        val existingPlaceOptional = this.placeRepository.findById(placeId)
        if (!existingPlaceOptional.isPresent) {
            return ResponseEntity.notFound().build()
        }
        val existingPlace = existingPlaceOptional.get()
        existingPlace.name = place.name
        existingPlace.description = place.description
        val updatedPlace = this.placeRepository.save(existingPlace)
        return ResponseEntity.ok().body(updatedPlace)
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'EDITOR')")
    @DeleteMapping("/{placeId}")
    fun deletePlace(@PathVariable(value = "placeId") placeId: Long): ResponseEntity<Void> {
        if (!this.placeRepository.existsById(placeId)) {
            return ResponseEntity.notFound().build()
        }
        this.placeRepository.deleteById(placeId)
        return ResponseEntity.ok().build()
    }
}
