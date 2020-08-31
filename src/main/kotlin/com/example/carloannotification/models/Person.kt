package com.example.carloannotification.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.MappedCollection

data class Person(
        @Id val id: Long,
        val firstName: String = "",
        val lastName: String = ""
        //@MappedCollection(idColumn = "owner_id") var certificates: List<Certificate> = emptyList()
)