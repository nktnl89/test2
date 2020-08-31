package com.example.carloannotification.job

import org.springframework.batch.core.partition.support.Partitioner
import org.springframework.batch.item.ExecutionContext
import java.util.*


class PersonPartitioner : Partitioner {
    override fun partition(gridSize: Int): MutableMap<String, ExecutionContext> {
        val partitionMap: MutableMap<String, ExecutionContext> = HashMap()
        var pageNumber = 0
        for (i in 0 until gridSize) {
            val ctxMap = ExecutionContext()
            ctxMap.putInt("pageNumber", pageNumber)
            pageNumber = pageNumber + 1
            partitionMap["Thread:-$i"] = ctxMap
        }
        return partitionMap
    }

}