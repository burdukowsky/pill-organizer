package tk.burdukowsky.pillOrganizerApi.user

import javax.persistence.*

@Entity
@Table(name = "roles")
class Role(
        @Id
        @Enumerated(value = EnumType.STRING)
        val name: RoleEnum = RoleEnum.VIEWER
) {
    fun authority(): String {
        return "ROLE_" + this.name.name
    }
}
