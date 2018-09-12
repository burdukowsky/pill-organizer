package tk.burdukowsky.pillOrganizerApi.place

import tk.burdukowsky.pillOrganizerApi.pill.Pill
import javax.persistence.*

@Entity
@Table(name = "places")
class Place(
        @Id
        @SequenceGenerator(name = "places_sequence", sequenceName = "places_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "places_sequence")
        val id: Long = 0,
        val name: String = "",
        @Column(columnDefinition = "text")
        val description: String = "",
        @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
        @JoinTable(
                name = "pills_places",
                joinColumns = [JoinColumn(name = "place_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "pill_id", referencedColumnName = "id")])
        var pills: List<Pill> = ArrayList()
)
