package com.example.carloannotification.job.skippers

import com.example.carloannotification.exceptions.IllegalBenedictLastNameException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.batch.core.step.skip.SkipPolicy

class PersonLastNameVerificationSkipper : SkipPolicy {

    private val logger: Logger = LoggerFactory.getLogger("badRecordLogger")

    override fun shouldSkip(t: Throwable, skipCount: Int): Boolean {
        if (t is IllegalBenedictLastNameException) {
            logger.error(t.message)
            return false
        }
        return false
    }
}