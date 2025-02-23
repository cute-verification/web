package io.github.gdrfgdrf.cuteverification.web.commons.json

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ObjectNode
import java.io.File
import java.io.InputStream
import kotlin.collections.get

@Suppress("UNCHECKED_CAST")
object Jsons {
    private val mapper = ObjectMapper()

    fun <T> read(string: String, type: Class<*>): T {
        return mapper.readValue(string, type) as T
    }

    fun <T> read(file: File, type: Class<*>): T {
        return mapper.readValue(file, type) as T
    }

    fun <T> read(inputStream: InputStream, type: Class<*>): T {
        return mapper.readValue(inputStream, type) as T
    }

    fun <T> read(bytes: ByteArray, type: Class<*>): T {
        return mapper.readValue(bytes, type) as T
    }

    fun new(): ObjectNode {
        return mapper.readTree("{}") as ObjectNode
    }

    fun read(string: String): CustomJsonNode {
        return CustomJsonNode(mapper.readTree(string))
    }

    fun read(file: File): CustomJsonNode {
        return CustomJsonNode(mapper.readTree(file))
    }

    fun read(inputStream: InputStream): CustomJsonNode {
        return CustomJsonNode(mapper.readTree(inputStream))
    }

    fun read(bytes: ByteArray): CustomJsonNode {
        return CustomJsonNode(mapper.readTree(bytes))
    }

    fun <E> list(string: String, eType: Class<E>): List<E> {
        val result: MutableList<E> = ArrayList()
        val jsonNode = read(string)
        if (jsonNode.node.isArray) {
            for (i in 0 until jsonNode.size()) {
                val e = read<E>(jsonNode[i], eType)
                result.add(e)
            }
        }

        return result
    }

    fun write(obj: Any): String {
        return mapper.writeValueAsString(obj)
    }
}