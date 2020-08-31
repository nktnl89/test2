package com.example.carloannotification.configurations

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource
import liquibase.integration.spring.SpringLiquibase

@Configuration
class LiquibaseConf {
    @Bean
    fun liquibase(ds: DataSource?): SpringLiquibase {
        val springLiquibase = SpringLiquibase()
        springLiquibase.setChangeLog("classpath:db/init-changelog.xml")
        springLiquibase.setDataSource(ds)
        return springLiquibase
    }
}