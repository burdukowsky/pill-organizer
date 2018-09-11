package tk.burdukowsky.pillOrganizerApi.pill

import javax.persistence.*

@Entity
@Table(name = "pills")
class Pill(
        @Id
        @SequenceGenerator(name = "pills_sequence", sequenceName = "pills_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pills_sequence")
        val id: Long = 0,
        val name: String = "",
        @Column(columnDefinition = "text")
        val description: String = ""
)
