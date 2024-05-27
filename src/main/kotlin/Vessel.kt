package org.parasmathur

data class Vessel(val lightMarks: List<String>) {
    val direction: Direction
        get() {
            val lights = lightMarks.mapNotNull { Light.apply(it) }
            return if (lights.size == lightMarks.size) {
                Direction.apply(lights)
            } else {
                Direction.UNKNOWN
            }
        }
}