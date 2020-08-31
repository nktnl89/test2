package com.example.carloannotification.configurations

import com.example.carloannotification.exceptions.IllegalBenedictLastNameException
import com.example.carloannotification.job.CarLoanCertificationExaminationProcessor
import com.example.carloannotification.job.CarLoanCertificationExaminationReader
import com.example.carloannotification.job.CarLoanCertificationExaminationWriter
import com.example.carloannotification.job.CustomMultiResourcePartitioner
import com.example.carloannotification.job.listeners.IllegalBenedictLastNameListener
import com.example.carloannotification.models.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.item.ItemReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.TaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate


@Configuration
@EnableBatchProcessing
class BatchConf(
        val restTemplate: RestTemplate,
        val jobBuilderFactory: JobBuilderFactory,
        val stepBuilderFactory: StepBuilderFactory,
        val carLoanCertificationExaminationProcessor: CarLoanCertificationExaminationProcessor,
        val carLoanCertificationExaminationWriter: CarLoanCertificationExaminationWriter
) {


    @Bean
    fun carLoanCertificationExaminationJob(): Job {
        return jobBuilderFactory.get("car-loan-certification-examination-job")
                .start(masterStep())
                .build()
    }

    @Bean
    fun masterStep(): Step {
        return stepBuilderFactory.get("masterStep")
                .partitioner("slaveStep", partitioner())
                .step(slaveStep())
                //.taskExecutor(taskExecutor())
                //.partitionHandler(partitionHandler())
                .build()
    }


    @Bean
    fun taskExecutor(): TaskExecutor {
        val taskExecutor = ThreadPoolTaskExecutor()
        taskExecutor.maxPoolSize = 5
        taskExecutor.corePoolSize = 5
        taskExecutor.setQueueCapacity(5)
        taskExecutor.afterPropertiesSet()
        return taskExecutor
    }

    @Bean
    fun partitioner(): CustomMultiResourcePartitioner {
        val partitioner = CustomMultiResourcePartitioner()
        val resources = listOf("http://localhost:8080/persons?page=0&size=10")//, "http://localhost:8080/persons?page=1&size=10")
        partitioner.resources = resources
        return partitioner
    }

    @Bean
    fun slaveStep(): Step {

        return stepBuilderFactory.get("slaveStep")
                //.transactionManager(platformTransactionManager)
                .chunk<Person, Person>(10)
                .reader(carLoanCertificationExaminationReader(null))
                .processor(carLoanCertificationExaminationProcessor)
                .writer(carLoanCertificationExaminationWriter)
                .faultTolerant()
                .skip(IllegalBenedictLastNameException::class.java)
                .skipLimit(3)
                .listener(IllegalBenedictLastNameListener())

                .build()
    }

    @Bean
    @StepScope
    fun carLoanCertificationExaminationReader(
            @Value("#{stepExecutionContext[page]}")
            apiUrl: String?): ItemReader<Person> {
        return CarLoanCertificationExaminationReader(apiUrl, restTemplate)
    }

}