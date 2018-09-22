package tk.burdukowsky.pillOrganizerApi.pill

import javax.persistence.*
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "pills")
class Pill(
        @Id
        @SequenceGenerator(name = "pills_sequence", sequenceName = "pills_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pills_sequence")

        var id: Long = 0,

        @field:NotEmpty
        val name: String = "",

        @Column(columnDefinition = "text")
        val description: String = ""
)
