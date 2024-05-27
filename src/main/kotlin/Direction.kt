package org.parasmathur

enum class Direction {
    TOWARDS,
    AWAY,
    LEFT,
    RIGHT,
    UNKNOWN;

    companion object {
        fun apply(lights: List<Light>): Direction {
            return when {
                lights.size == 2 && lights.containsAll(listOf(Light.RED, Light.GREEN)) -> TOWARDS
                lights.size == 1 && lights.contains(Light.WHITE) -> AWAY
                lights.size == 1 && lights.contains(Light.RED) -> LEFT
                lights.size == 1 && lights.contains(Light.GREEN) -> RIGHT
                else -> UNKNOWN
            }
        }
    }
}
