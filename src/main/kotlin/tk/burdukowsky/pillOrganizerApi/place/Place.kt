package tk.burdukowsky.pillOrganizerApi.place

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "places")
class Place(
        @Id
        @GeneratedValue
        val id: Long = 0,
        val name: String = "",
        val description: String = ""
)
