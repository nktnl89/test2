package com.example.carloannotification.services.mappers

import com.example.carloannotification.models.Certificate
import com.example.carloannotification.models.Person
import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.util.*


class PersonMapper : RowMapper<Person> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Person? {
        if (rs.next()) {
            val personId = rs.getLong("person_id")
            val firstName = rs.getString("first_name")
            val lastName = rs.getString("last_name")

            return Person(personId, firstName, lastName)//, listOf(certificate))
        }
        return null
    }
}