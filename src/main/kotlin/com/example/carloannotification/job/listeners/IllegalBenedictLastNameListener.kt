package com.example.carloannotification.job.listeners

import com.example.carloannotification.exceptions.IllegalBenedictLastNameException
import com.example.carloannotification.models.Person
import liquibase.pro.packaged.t
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.SkipListener

import java.lang.Exception
import javax.batch.api.chunk.listener.SkipReadListener


class IllegalBenedictLastNameListener : SkipListener<Person, Person> {
    private val logger: Logger = LoggerFactory.getLogger("badRecordLogger")
    override fun onSkipInWrite(item: Person, t: Throwable) {
        if (t is IllegalBenedictLastNameException) {
            logger.error("${t.message} with $item")
        }
    }

    override fun onSkipInRead(t: Throwable) {
        if (t is IllegalBenedictLastNameException) {
            logger.error(t.message)
        }
    }

    override fun onSkipInProcess(item: Person, t: Throwable) {
        if (t is IllegalBenedictLastNameException) {
            logger.error("${t.message} with $item")
        }
    }
//    override fun onSkipReadItem(ex: Exception?) {
//        if (ex is IllegalBenedictLastNameException) {
//            logger.error(ex.message)
//        }
//    }


}