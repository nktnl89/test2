package com.example.carloannotification.configurations

//import com.example.carloannotification.job.PaginatedReader

import com.example.carloannotification.job.CarLoanCertificationExaminationProcessor
import com.example.carloannotification.job.CarLoanCertificationExaminationWriter
import com.example.carloannotification.job.PaginatedReader
import com.example.carloannotification.models.Person
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.partition.PartitionHandler
import org.springframework.batch.core.partition.support.SimplePartitioner
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler
import org.springframework.batch.item.data.AbstractPaginatedDataItemReader
import org.springframework.batch.item.file.MultiResourceItemReader
import org.springframework.batch.item.file.builder.MultiResourceItemReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.web.client.RestTemplate


//@EnableBatchProcessing
//@Configuration
class BatchPartitionConf(

        val restTemplate: RestTemplate,
        val transactionManager: PlatformTransactionManager,
        val jobBuilderFactory: JobBuilderFactory,
        val stepBuilderFactory: StepBuilderFactory,
        val carLoanCertificationExaminationProcessor: CarLoanCertificationExaminationProcessor,
        val carLoanCertificationExaminationWriter: CarLoanCertificationExaminationWriter
) {

    @Bean
    fun job(): Job {
        return jobBuilderFactory.get("job")
                .start(masterStep())
                .build()
    }

    @Bean
    fun masterStep(): Step {
        return stepBuilderFactory.get("masterStep")
                .partitioner(slaveStep().name, simplePartitioner())
                .partitionHandler(partitionHandler())
                .build()
    }

    @Bean
    fun simpleTaskExecutor(): SimpleAsyncTaskExecutor {
        return SimpleAsyncTaskExecutor()
    }

    @Bean
    fun threadPoolTaskExecutor(): ThreadPoolTaskExecutor {
        val threadPoolTaskExecutor = ThreadPoolTaskExecutor()
        threadPoolTaskExecutor.corePoolSize = 4
        threadPoolTaskExecutor.maxPoolSize = 4
        return threadPoolTaskExecutor

    }

    @Bean
    fun simplePartitioner(): SimplePartitioner {
        return SimplePartitioner()
    }

    @Bean
    fun partitionHandler(): PartitionHandler {
        val handler = TaskExecutorPartitionHandler()
        handler.setTaskExecutor(simpleTaskExecutor())
        //handler.setTaskExecutor(threadPoolTaskExecutor())
        handler.step = slaveStep()
        handler.gridSize = 4
        return handler
    }

    @Bean
    fun slaveStep(): Step {
        return stepBuilderFactory.get("car-loan-certification-examination-step")
                .transactionManager(transactionManager)
                .chunk<Person, Person>(10)
                .reader(carLoanCertificationExaminationReader())
                .processor(carLoanCertificationExaminationProcessor)
                .writer(carLoanCertificationExaminationWriter)
                .build()
    }

    @Bean
    fun carLoanCertificationExaminationReader(
            //@Value("#{jobParameters['inputFlatFile']}") resource: Resource?
    ): AbstractPaginatedDataItemReader<Person> {

        val reader = PaginatedReader("http://localhost:8080/persons?page=1&size=10", restTemplate)
        reader.setName("reader")
        return reader
    }




}