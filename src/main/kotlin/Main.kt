package org.parasmathur

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    data class LightsCase(val options: String, val result: Light?)
    data class DirectionCase(val options: List<Light>, val result: Direction)
    data class VesselCase(val options: List<String>, val result: Direction)

    fun checkLights(assertion: LightsCase, result: Light?) {
        if (assertion.result == result) {
            println("SUCCESS: ${assertion.options} returned $result")
        } else {
            println("FAILURE: ${assertion.options} returned $result, expected ${assertion.result}")
        }
    }

    fun checkDirection(assertion: DirectionCase, result: Direction) {
        if (assertion.result == result) {
            println("SUCCESS: ${assertion.options} returned $result")
        } else {
            println("FAILURE: ${assertion.options} returned $result, expected ${assertion.result}")
        }
    }

    fun checkVessel(assertion: VesselCase, result: Direction) {
        if (assertion.result == result) {
            println("SUCCESS: ${assertion.options} returned $result")
        } else {
            println("FAILURE: ${assertion.options} returned $result, expected ${assertion.result}")
        }
    }

    println("Checking Lights...")

    listOf(
        LightsCase("r", Light.RED), LightsCase("R", Light.RED),
        LightsCase("w", Light.WHITE), LightsCase("W", Light.WHITE),
        LightsCase("g", Light.GREEN), LightsCase("G", Light.GREEN),
        LightsCase("c", null), LightsCase("235", null), LightsCase("sa24r", null)
    ).forEach { checkLights(it, Light.apply(it.options)) }

    println("Checking Directions...")
    listOf(
        DirectionCase(listOf(Light.WHITE), Direction.AWAY),
        DirectionCase(listOf(Light.GREEN, Light.RED), Direction.TOWARDS),
        DirectionCase(listOf(Light.RED, Light.GREEN), Direction.TOWARDS),
        DirectionCase(listOf(Light.GREEN), Direction.RIGHT),
        DirectionCase(listOf(Light.RED), Direction.LEFT),
        DirectionCase(listOf(Light.RED, Light.WHITE), Direction.UNKNOWN),
        DirectionCase(listOf(Light.WHITE, Light.RED), Direction.UNKNOWN),
        DirectionCase(listOf(Light.GREEN, Light.WHITE), Direction.UNKNOWN),
        DirectionCase(listOf(Light.WHITE, Light.GREEN), Direction.UNKNOWN),
        DirectionCase(listOf(Light.RED, Light.WHITE, Light.GREEN), Direction.UNKNOWN),
        DirectionCase(listOf(Light.WHITE, Light.RED, Light.GREEN), Direction.UNKNOWN),
        DirectionCase(listOf(Light.WHITE, Light.GREEN, Light.RED), Direction.UNKNOWN),
        DirectionCase(listOf(Light.GREEN, Light.WHITE, Light.RED), Direction.UNKNOWN),
    ).forEach { checkDirection(it, Direction.apply(it.options)) }

    println("Checking Vessels...")
    listOf(
        VesselCase(listOf("xsaf", "g"), Direction.UNKNOWN),
        VesselCase(listOf("r", "g"), Direction.TOWARDS),
        VesselCase(listOf("w", "g"), Direction.UNKNOWN),
        VesselCase(listOf("w", "r"), Direction.UNKNOWN),
        VesselCase(listOf("g"), Direction.RIGHT)
    ).forEach { checkVessel(it, Vessel(it.options).direction) }

    val horizon = List(360) { listOf<String>() }
        .toMutableList()
        .apply {
            this[0] = listOf("b") // Invalid lightMark for testing
            this[3] = listOf("R")
            this[5] = listOf("R", "G")
            this[14] = listOf("W")
            this[19] = listOf("W")
            this[21] = listOf("R")
            this[26] = listOf("G")
            this[35] = listOf("R", "G")
            this[42] = listOf("R", "G")
            this[47] = listOf("R", "G")
            this[55] = listOf("R", "G")
            this[67] = listOf("W", "G")
            this[74] = listOf("R", "G")
            this[78] = listOf("W")
            this[82] = listOf("R")
            this[95] = listOf("R", "G")
            this[137] = listOf("R", "G")
            this[145] = listOf("R", "G")
            this[172] = listOf("R", "G")
            this[182] = listOf("W")
            this[198] = listOf("R", "G")
            this[207] = listOf("R", "G")
            this[212] = listOf("R", "G")
            this[229] = listOf("R", "G")
            this[231] = listOf("R", "G")
            this[246] = listOf("R", "G")
            this[259] = listOf("R", "G")
            this[263] = listOf("R", "G")
            this[301] = listOf("R", "G")
            this[328] = listOf("R", "G")
            this[346] = listOf("R", "G")
            this[358] = listOf("R", "G")
            this[359] = listOf("W")
        }

    println("Checking Binoculars.countVessels(horizon, 0, 30, _ => true)")
    // 0 - 15 = 4 + 345 - 360 = 3 => 7
    val case1 = Binoculars.countVessels(horizon, 0, 30) { true }
    if (case1 == 7) {
        println("SUCCESS: Binoculars.countVessels(horizon, 0, 30, _ => true) returned $case1")
    } else {
        println("FAILURE: Binoculars.countVessels(horizon, 0, 30, _ => true) returned $case1, expected 7")
    }

    println("Checking Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Unknown)")
    // Just one Seq("b")
    val case11 = Binoculars.countVessels(horizon, 0, 30) { it.direction == Direction.UNKNOWN }
    if (case11 == 1) {
        println("SUCCESS: Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Unknown) returned $case11")
    } else {
        println("FAILURE: Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Unknown) returned $case11, expected 1")
    }

    println("Checking Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Unknown)")
    // Just one Seq("b")
    val case12 = Binoculars.countVessels(horizon, 0, 30) { it.direction == Direction.AWAY }
    if (case12 == 2) {
        println("SUCCESS: Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Away) returned $case12")
    } else {
        println("FAILURE: Binoculars.countVessels(horizon, 0, 30, vessel => vessel.direction == Away) returned $case12, expected 2")
    }

    println("Checking Binoculars.countVessels(horizon, 15, 60, _ => true)")
    // 15 - 45 = 5 + 345 - 15 = 7 => 12
    val case2 = Binoculars.countVessels(horizon, 15, 60) { true }
    if (case2 == 12) {
        println("SUCCESS: Binoculars.countVessels(horizon, 15, 60, _ => true) returned $case2")
    } else {
        println("FAILURE: Binoculars.countVessels(horizon, 15, 60, _ => true) returned $case2, expected 12")
    }

    println("Binoculars.countVessels(horizon, 350, 80, _ => true)")
    // 350 - 310 = 2 + 350 - 30 = 9 => 11
    val case4 = Binoculars.countVessels(horizon, 350, 80) { true }
    if (case4 == 11) {
        println("SUCCESS: Binoculars.countVessels(horizon, 350, 80, _ => true) returned $case4")
    } else {
        println("FAILURE: Binoculars.countVessels(horizon, 350, 80, _ => true) returned $case4, expected 11")
    }

    println("Binoculars.mostVessels(horizon, 30)")
    val case5 = Binoculars.mostVessels(horizon, 30)
    // most of them with a 30 angle are between 358 - 28 = 9 , angle should be 13
    if (case5 == 13) {
        println("SUCCESS: Binoculars.mostVessels(horizon, 30) returned $case5")
    } else {
        println("FAILURE: Binoculars.mostVessels(horizon, 30) returned $case5, expected 13")
    }

    println("Binoculars.mostVessels(horizon, 20)")
    // most of them with a 20 angle are between 359 - 19 = 6 , angle should be 9
    val case6 = Binoculars.mostVessels(horizon, 20)
    if (listOf(4, 5, 6, 7, 8, 9, 355, 356).contains(case6)) {
        println("SUCCESS: Binoculars.mostVessels(horizon, 20) returned $case6")
    } else {
        println("FAILURE: Binoculars.mostVessels(horizon, 20) returned $case6, expected 9")
    }

}