package com.example.carloannotification.job

import com.example.carloannotification.models.Person
import com.example.carloannotification.models.RestResponsePage
import liquibase.pro.packaged.T
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList

class CarLoanCertificationExaminationReader(
        val apiUrl: String?,
        val restTemplate: RestTemplate

) : ItemReader<Person> {//AbstractPaginatedDataItemReader<Person>()

    private var nextPersonIndex = 0
    private val logger: Logger = LoggerFactory.getLogger(javaClass)


    override fun read(): Person? {
        var personData: List<Person>? = null
        if (personData == null) {
            personData = restTemplate.getForObject(apiUrl!!, Array<Person>::class.java)!!.toList()
        }

        var nextPerson: Person? = null
        if (nextPersonIndex < personData.size) {
            nextPerson = personData[nextPersonIndex]
            nextPersonIndex++
        }

        if (nextPerson != null) {
            logger.info("${Thread.currentThread().getName()} reading partitionData = ${nextPerson.id}")
        }
//        if (nextPerson != null) {
//            val lastName = nextPerson.lastName
//            if (!lastName.equals("Cumberbatch")) {
//                throw IllegalBenedictLastNameException("ALARM!!!!!! WRONG LAST NAME: $lastName")
//            }
//        }
        return nextPerson
    }

//    override fun doPageRead(): MutableIterator<Person> {
//
//        var response: ResponseEntity<*> = restTemplate.getForEntity<RestResponsePage<Person>>(apiUrl!!)
//        val persons: MutableList<Person> = ArrayList()
//
//        persons.addAll((response.body as RestResponsePage<Person>).content)
//
//        return persons.iterator()
//    }
}