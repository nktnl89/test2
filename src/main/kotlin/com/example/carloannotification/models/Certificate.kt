package com.example.carloannotification.models

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class Certificate(
        @Id val id: Long?,
        val owner: Person,
        val expirationDate: LocalDate,
        val rate: Int
) {
}