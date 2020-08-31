package com.example.carloannotification.job

import com.example.carloannotification.exceptions.IllegalBenedictLastNameException
import com.example.carloannotification.models.Certificate
import com.example.carloannotification.models.Person
import com.example.carloannotification.repositories.CertificateRepo
import com.example.carloannotification.repositories.PersonRepo
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.item.ItemProcessor
import org.springframework.stereotype.Component

@Component
class CarLoanCertificationExaminationProcessor(
        val personRepo: PersonRepo,
        val certificateRepo: CertificateRepo
) : ItemProcessor<Person, Person> {
    private val logger: Logger = LoggerFactory.getLogger(javaClass)

    override fun process(item: Person): Person {


//        val lastName = item.lastName
//        if (!lastName.equals("Cumberbatch")) {
//            throw IllegalBenedictLastNameException("ALARM!!!!!! WRONG LAST NAME: $lastName")
//        }
        logger.info("${Thread.currentThread().getName()} processing partitionData = ${item.id}");
//        for (cert in item.certificates) {
//            if (!certificateRepo.existsById(cert.id!!)) {
//                certificateRepo.save(Certificate(cert.expirationDate, cert.rate))
//            }
//        }
        //personRepo.save(item)
        return Person(item.id, item.firstName.toUpperCase(), item.lastName.toUpperCase())
    }
}