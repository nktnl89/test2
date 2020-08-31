package com.example.carloannotification.services

import com.example.carloannotification.models.Person
import com.example.carloannotification.repositories.PersonRepo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service

@Service
class PersonService(
        val jdbcTemplate: JdbcTemplate
        //val personRepo: PersonRepo
) {
//    fun finById(id: Long): Person? {
//        return personRepo.findById(id).orElse(null)
//    }
//
//    fun save(person: Person) {
//        personRepo.save(person)
//    }
//
//    fun saveAll(persons: Iterable<Person>) {
//        personRepo.saveAll(persons)
//    }
    val allPersons: List<Person>
        get() = jdbcTemplate.queryForList(
                "select" +
                        "   p.id," +
                        "   p.first_name," +
                        "   p.last_name," +
                        "   c.id certificate_id," +
                        "   c.expiration_date expiration_date," +
                        "   c.rate rate" +
                        "   from persons p" +
                        "   left join certificate c on p.id = c.owner_id",
                Person::class.java)
//        ) { rs, _ ->
//            Person(rs.getLong("id"), rs.getString("first_name"),
//                    rs.getString("last_name"))
//
//        }

    fun create(firstName: String, lastName: String) {
        jdbcTemplate.update("insert into persons(first_name, last_name) values(?,?)",
                firstName, lastName)
    }

    fun finById(id: Long) {
        jdbcTemplate.queryForObject("select" +
                "   p.id person_id," +
                "   p.first_name first_name," +
                "   p.last_name last_name," +
                "   c.id certificate_id," +
                "   c.expiration_date expiration_date," +
                "   c.rate rate" +
                "   from persons p " +
                "   left join certificate c on p.id = c.owner_id",
                Person::class.java)
    }
}