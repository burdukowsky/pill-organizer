package tk.burdukowsky.pillOrganizerApi.pill

import org.springframework.data.jpa.repository.JpaRepository

interface PillRepository : JpaRepository<Pill, Long>
