package tk.burdukowsky.pillOrganizerApi.place

import org.springframework.data.jpa.repository.JpaRepository

interface PlaceRepository : JpaRepository<Place, Long>
