package tk.burdukowsky.pillOrganizerApi.place

import javax.persistence.*

@Entity
@Table(name = "places")
class Place(
        @Id
        @SequenceGenerator(name = "places_sequence", sequenceName = "places_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "places_sequence")
        val id: Long = 0,
        val name: String = "",
        val description: String = ""
)
