package com.example.carloannotification.services

import com.example.carloannotification.models.Certificate
import com.example.carloannotification.repositories.CertificateRepo
import com.example.carloannotification.repositories.PersonRepo
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class CertificateService(
        //val jdbcTemplate: JdbcTemplate,
        val certificateRepo: CertificateRepo

) {
    fun create(certificate: Certificate) {
        certificateRepo.save(certificate)
    }
//    override fun create(ownerId: Long, expirationDate: LocalDate, rate: Int) {
//        jdbcTemplate.update("insert into certificates(owner_id, expiration_date, rate) values(?,?,?)",
//                ownerId, expirationDate, rate)
//    }
}