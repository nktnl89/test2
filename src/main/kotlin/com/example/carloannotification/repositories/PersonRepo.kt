package com.example.carloannotification.repositories

import com.example.carloannotification.models.Person
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

//@Repository
interface PersonRepo : PagingAndSortingRepository<Person, Long> {
}