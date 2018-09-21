package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface PlaceRepository : JpaRepository<Place, Long> {
    @Transactional
    @Modifying
    @Query("delete from pills_places where pill_id = ?1 and place_id = ?2", nativeQuery = true)
    fun deletePillPlace(pillId: Long, placeId: Long)
}
