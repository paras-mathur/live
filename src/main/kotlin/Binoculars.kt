package org.parasmathur

object Binoculars {

    /**
     * Counts vessels on the horizon, given the direction they are pointed to
     * @param lightMarksOnHorizon the entire horizon 0 - 360
     * @param center - degree number where the binoculars are pointing
     * @param angle - the zoom level, how many degrees of the horizon are visible
     * @param directionFilter - the filter function which defines whether we're searching for vessels heading left, right, or ...
     * @return the number of ships visible in the subset of the horizon narrowed by the angle, applying the direction filter
     */
    fun countVessels(
        lightMarksOnHorizon: List<List<String>>,
        center: Int,
        angle: Int,
        directionFilter: (Vessel) -> Boolean
    ): Int {
        /**
         * Create range of degrees here with return type as List<Int>
         *     Ex: If the center is '0' and the angle is '30' then visible range is from -15 to 15
         */
        val visibleRange : List<Int> = (center - angle / 2..center + angle / 2).map { it.mod(360) }
        var vesselCount = 0
        for (index in visibleRange) {
            val lightMarks = lightMarksOnHorizon[index]
            if (lightMarks.isNotEmpty()) {
                val vessel = Vessel(lightMarks)
                if (directionFilter(vessel)) {
                    vesselCount++
                }
            }
        }
        return vesselCount
    }

    /**
     * Finds the degree to which binoculars should be pointed, where most vessels can be seen.
     * @param lightMarksOnHorizon the entire horizon 0 - 360
     * @param angle - the zoom level, how many degrees of the horizon are visible
     * @return the center of the binoculars where they should be pointed to
     */
    fun mostVessels1(lightMarksOnHorizon: List<List<String>>, angle: Int): Int {
        val horizon = lightMarksOnHorizon.size
        require(horizon == 360) { "The horizon should have 360 degrees" }

        /**
         *  Convert lightMarksOnHorizon to a list of Vessel counts
         *  We'll have vesselCounts as List with 360 records
         */
        val vesselCounts = lightMarksOnHorizon.map { lightMarks ->
            lightMarks.count { mark ->
                Light.apply(mark) != null
            }
        }

        /**
         *  Initialize the sliding window sum and max count
         *  'currentSum' is the initial sum with our window size as 'angle'
         *      Ex: If the angle is '6' - it should include 0,1,2,3,4,5,6 i.e. 7 elements in total
         */
        var currentSum = 0
        for (i in 0 until angle+1) {
            currentSum += vesselCounts[i]
        }

        var maxSum = currentSum
        var bestCenter = angle / 2

        // Slide the window across the horizon i.e. 360 records
        for (i in 1 until horizon) {
//            currentSum -= vesselCounts[(i - 1) % horizon]
//            currentSum += vesselCounts[(i + angle - 1) % horizon]

            currentSum = currentSum - vesselCounts[(i - 1 + horizon) % horizon] + vesselCounts[(i + angle - 1) % horizon]


            // only update the bestCenter when maxSum is bigger than currentSum
            if (currentSum > maxSum) {
                maxSum = currentSum
                bestCenter = (i + angle / 2) % horizon
            }
        }

        return bestCenter
    }

    // Convert lightMarksOnHorizon to a list of vessel counts
    private fun vesselCountsPerDegree(lightMarksOnHorizon: List<List<String>>): List<Int> {
        return lightMarksOnHorizon.map { lightMarks ->
            if (lightMarks.isNotEmpty()) 1 else 0
        }
    }

    fun mostVessels(lightMarksOnHorizon: List<List<String>>, angle: Int): Int {
        val n = lightMarksOnHorizon.size
        require(n == 360) { "The horizon should have 360 degrees" }

        // Convert lightMarksOnHorizon to a list of Vessel counts
        val vesselCounts = vesselCountsPerDegree(lightMarksOnHorizon)

        // Sliding window approach with circular handling
        var currentSum = 0
        for (i in 0 until angle) {
            currentSum += vesselCounts[i]
        }

        var maxSum = currentSum
        var bestCenter = angle / 2

        for (i in 1 until n) {
            currentSum = currentSum - vesselCounts[(i - 1 + n) % n] + vesselCounts[(i + angle - 1) % n]

            if (currentSum >= maxSum) { // >= is used to capture the last encountered value in our rotational check with largestSum or highest number of vessels
                maxSum = currentSum
                bestCenter = (i + angle / 2) % n
            }
        }

        return bestCenter
    }

}
