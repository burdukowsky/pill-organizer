package tk.burdukowsky.pillOrganizerApi.user

import javax.persistence.*
import java.util.HashSet

@Entity
@Table(name = "users")
class AppUser(
        @Id
        @SequenceGenerator(name = "users_sequence", sequenceName = "users_sequence", allocationSize = 5)
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_sequence")
        val id: Long = 0,
        val username: String = "",
        val password: String = "",
        @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
        @JoinTable(
                name = "users_roles",
                joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
                inverseJoinColumns = [JoinColumn(name = "role_name", referencedColumnName = "name")])
        private val roles: Set<Role> = HashSet()
)
