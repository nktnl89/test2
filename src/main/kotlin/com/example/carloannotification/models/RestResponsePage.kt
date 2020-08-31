package com.example.carloannotification.models

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

class RestResponsePage<T> : PageImpl<Person> {
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    constructor(@JsonProperty("content") content: List<Person>,
                @JsonProperty("number") number: Int,
                @JsonProperty("size") size: Int,
                @JsonProperty("totalElements") totalElements: Long?,
                @JsonProperty("pageable") pageable: JsonNode,
                @JsonProperty("last") last: Boolean,
                @JsonProperty("totalPages") totalPages: Int,
                @JsonProperty("sort") sort: JsonNode,
                @JsonProperty("first") first: Boolean,
                @JsonProperty("numberOfElements") numberOfElements: Int) : super(content, PageRequest.of(number, size), totalElements!!) {
    }

    constructor(content: List<Person>, pageable: Pageable, total: Long) : super(content, pageable, total) {}

    constructor(content: List<Person>) : super(content) {}

    constructor() : super(ArrayList<Person>()) {}
}