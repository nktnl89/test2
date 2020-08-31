package com.example.carloannotification.tasks

import net.javacrumbs.shedlock.spring.annotation.SchedulerLock
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecutionException
import org.springframework.batch.core.JobParameter
import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class NotifyShedlockTask {

    @Autowired
    val jobLauncher: JobLauncher? = null

    @Autowired
    val carLoanCertificationExaminationJob: Job? = null


    @SchedulerLock(name = "TaskScheduler_scheduledTask", lockAtLeastFor = "10S", lockAtMostFor = "30S")//, lockAtLeastFor = "PT1M" lockAtMostFor = "PT14M"
    @Scheduled(cron = "* * * ? * *")
    fun scheduledTask() {
        try {
            val timestampParam = JobParameter(Date(), true)
            jobLauncher!!.run(
                    carLoanCertificationExaminationJob!!,
                    JobParameters(mutableMapOf("timestamp" to timestampParam)))
        } catch (e: JobExecutionException) {
            println(e.message)
        }
        Thread.sleep(10000)
    }

}
