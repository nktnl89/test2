package com.example.carloannotification.job

import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext
import java.util.*
import kotlin.collections.ArrayList

class CustomMultiResourcePartitioner : Partitioner {
    var resources: List<String>? = ArrayList()
    var keyName = "page"

    override fun partition(gridSize: Int): Map<String, ExecutionContext> {
        val map: MutableMap<String, ExecutionContext> = HashMap(gridSize)
        var i = 0
        for (resource in resources!!) {
            val context = ExecutionContext()
            context.putString(keyName, resource)
            context.putString("size", "10")
            map["partition" + i] = context
            i++
        }
        return map
    }
}