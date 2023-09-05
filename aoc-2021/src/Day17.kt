private val testInput = readInput("Day17_test")
private val realInput = readInput("Day17")

fun main() {
    class Velocity(var x: Int, var y: Int) {
        fun applyDrag(): Velocity {
            if (this.x > 0) {
                this.x -= 1
            }
            if (this.x < 0) {
                this.x += 1
            }
            return this
        }

        fun applyGravity(): Velocity {
            this.y -= 1
            return this
        }

        fun update(coord: Coordinate): Coordinate {
            return Coordinate(coord.x + x, coord.y + y)
        }

        fun clone(): Velocity {
            return Velocity(this.x, this.y)
        }

        override fun equals(other: Any?): Boolean {
            if (other !is Velocity) {
                return false
            }
            return this.x == other.x && this.y == other.y
        }

        override fun hashCode(): Int {
            var result = x
            result = 31 * result + y
            return result
        }
    }

    class Target(val xRange: IntRange, val yRange: IntRange, val debug: Boolean = false) {
        // ** Debug stuff **
        fun print(markers: MutableList<Coordinate>) {
            val maxX = maxOf(markers.maxOf { it.x }, xRange.last)
            val minY = minOf(markers.minOf { it.y }, yRange.first)
            val maxY = maxOf(markers.maxOf { it.y }, yRange.last)

            for (y in maxY toward minY) {
                var line = ""
                for (x in 0..maxX) {
                    line += if (markers.contains(Coordinate(x, y))) {
                        "#"
                    } else if (xRange.contains(x) && yRange.contains(y)) {
                        "T"
                    } else {
                        "."
                    }
                }
                println(line)
            }
            println("")
        }
        // ** Debug stuff **

        fun insideTarget(coord: Coordinate): Boolean {
            return coord.x in xRange && coord.y in yRange
        }

        fun pastTarget(coord: Coordinate, vel: Velocity): Boolean {
            return coord.x > xRange.last || coord.y < yRange.first && vel.y < 0
        }

        fun hitsTarget(vel: Velocity): Pair<Boolean, Int> {
            var insideTarget: Boolean = false
            var pastTarget: Boolean = false
            var currentVel = vel.clone()
            var currentCoord = Coordinate(0, 0)
            var maxY = 0
            val markers = mutableListOf(currentCoord)
            while (!insideTarget && !pastTarget) {
                currentCoord = currentVel.update(currentCoord)
                if (currentCoord.y > maxY) {
                    maxY = currentCoord.y
                }
                currentVel = currentVel.applyDrag().applyGravity()
                insideTarget = insideTarget(currentCoord)
                pastTarget = pastTarget(currentCoord, currentVel)
                markers.add(currentCoord)
            }
            // ** Debug stuff **
            if (debug) {
                this.print(markers)
            }
            // ** Debug stuff **
            return Pair(insideTarget, maxY)
        }
    }

    fun getTarget(input: List<String>, debug: Boolean): Target {
        fun removeTrailingComma(str: String): String {
            return if (str.last() == ',') str.dropLast(1) else str
        }

        val splitBySpaces = input[0].split(" ")
        val xRangeString = splitBySpaces[2].split("=")[1].split("..").map(::removeTrailingComma)
        val yRangeString = splitBySpaces[3].split("=")[1].split("..").map(::removeTrailingComma)

        val xRange = xRangeString[0].toInt()..xRangeString[1].toInt()
        val yRange = yRangeString[0].toInt()..yRangeString[1].toInt()

        return Target(xRange, yRange, debug)
    }

    fun maximiseY(target: Target): Int {
        var biggestMaxY = 0
        for (xVel in 5..target.xRange.first) {
            for (yVel in 5..1000) {
                val vel = Velocity(xVel, yVel)
                val (hitTarget, maxY) = target.hitsTarget(vel)
                if (hitTarget && maxY > biggestMaxY) {
                    biggestMaxY = maxY
                }
            }
        }
        return biggestMaxY
    }

    fun velocitiesWhichHitTarget(target: Target): List<Velocity> {
        val velocities = mutableListOf<Velocity>()
        for (xVel in -100..target.xRange.last) {
            for (yVel in -1000..1000) {
                val vel = Velocity(xVel, yVel)
                val (hitTarget) = target.hitsTarget(vel)
                if (hitTarget) {
                    velocities.add(vel)
                }
            }
        }
        return velocities
    }

    fun part1(input: List<String>, debug: Boolean): Int {
        val target = getTarget(input, debug)
        return maximiseY(target)
    }

    fun part2(input: List<String>, debug: Boolean): Int {
        val target = getTarget(input, debug)
        val velocitiesHittingTarget = velocitiesWhichHitTarget(target)
        return velocitiesHittingTarget.size
    }

    println(part1(testInput, false))
    println(part1(realInput, false))
    println(part2(testInput, false))
    println(part2(realInput, false))
}