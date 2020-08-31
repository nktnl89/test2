package com.example.carloannotification.configurations

import com.example.carloannotification.models.Person
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.QueryMappingConfiguration
import org.springframework.data.jdbc.repository.config.DefaultQueryMappingConfiguration
import org.springframework.web.client.RestTemplate


@Configuration
class RestConf {

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

//    @Bean
//    fun rowMappers(): QueryMappingConfiguration {
//        return DefaultQueryMappingConfiguration()
//                .register(Person::class.java, PersonRowMapper())
//                .register(Address::class.java, AddressRowMapper())
//    }
}