package io.github.gdrfgdrf.cuteverification.web.commons.json

import com.fasterxml.jackson.databind.JsonNode

class CustomJsonNode(
    val node: JsonNode
) {
    operator fun get(index: Int): String {
        return node.get(index).asText()
    }

    operator fun get(key: String): String {
        return node.get(key).asText()
    }

    fun getOrNull(index: Int): String? {
        if (contains(index)) {
            return get(index)
        }
        return null
    }

    fun getOrNull(key: String): String? {
        if (contains(key)) {
            return get(key)
        }
        return null
    }

    fun contains(index: Int): Boolean {
        return node.has(index)
    }

    fun contains(key: String): Boolean {
        return node.has(key)
    }

    fun size(): Int {
        return node.size()
    }

    fun keySet(): Iterator<String> {
        return node.fieldNames()
    }
}