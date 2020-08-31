package com.example.carloannotification.repositories

import com.example.carloannotification.models.Certificate
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

//@Repository
interface CertificateRepo : PagingAndSortingRepository<Certificate, Long> {
}