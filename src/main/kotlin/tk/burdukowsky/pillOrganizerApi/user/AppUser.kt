package tk.burdukowsky.pillOrganizerApi.user

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*
import java.util.HashSet
import javax.validation.constraints.NotEmpty

@Entity
@Table(name = "users")
class AppUser(
        @Id
        @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
        var id: Long = 0,

        @field:NotEmpty
        @Column(unique = true)
        val username: String = "",

        @get:JsonIgnore
        @set:JsonProperty
        @field:NotEmpty
        var password: String = "",

        @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
        @JoinTable(
                name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_name", referencedColumnName = "name")])
        val roles: Set<Role> = HashSet()
)
