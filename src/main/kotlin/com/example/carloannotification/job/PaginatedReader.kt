package com.example.carloannotification.job

import com.example.carloannotification.models.Person
import com.example.carloannotification.models.RestResponsePage
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity

class PaginatedReader(
        val apiUrl: String,
        val restTemplate: RestTemplate
) : AbstractPaginatedDataItemReader<Person>(), ItemReader<Person> {


    override fun doPageRead(): MutableIterator<Person> {

        var response: ResponseEntity<*> = restTemplate.getForEntity<RestResponsePage<Person>>(apiUrl)
        val persons: MutableList<Person> = ArrayList()
//        println(super.page)
//
//        println(pageSize)

        persons.addAll((response.body as RestResponsePage<Person>).content)

        return persons.iterator()
    }
}