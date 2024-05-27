package org.parasmathur

enum class Light(val mark: String) {
    RED("r"),
    GREEN("g"),
    WHITE("w");

    companion object {
        fun apply(mark: String): Light? {
            return entries.find { it.mark == mark.lowercase() }
        }
    }
}