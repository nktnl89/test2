package com.example.carloannotification

import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling


@SpringBootApplication
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "30S")
class CarLoanNotificationApplication

fun main(args: Array<String>) {
    runApplication<CarLoanNotificationApplication>(*args)
}
