package com.example.carloannotification.job

import com.example.carloannotification.exceptions.IllegalBenedictLastNameException
import com.example.carloannotification.models.Person
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemWriter
import org.springframework.stereotype.Component

@Component
class CarLoanCertificationExaminationWriter : ItemWriter<Person> {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)


    override fun write(items: MutableList<out Person>) {
        //logger.info("another 10 Benedicts")
        for (person in items) {

//            val lastName = person.lastName
//            if (!lastName.toLowerCase().equals("cumberbatch")) {
            //logger.info("${person.id} failed")
//                throw IllegalBenedictLastNameException("ALARM!!!!!! WRONG LAST NAME: $lastName")
//            }
            logger.info("${Thread.currentThread().getName()} writing partitionData = ${person.id}");
            //logger.info("writing ${person}")
        }
    }
}