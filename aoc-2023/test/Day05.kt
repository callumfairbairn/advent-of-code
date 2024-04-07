import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSourceToDestinationMap {
    @Test
    fun testOneToOneMap() {
        val testInput = listOf("1 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(1.toBigInteger(), sToDMap.get(1.toBigInteger()))
        assertEquals(2.toBigInteger(), sToDMap.get(2.toBigInteger()))
        assertEquals(3.toBigInteger(), sToDMap.get(3.toBigInteger()))
        assertEquals(4.toBigInteger(), sToDMap.get(4.toBigInteger()))
    }

    @Test
    fun testOneToOneMapRanges() {
        val testInput = listOf("1 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        val input = listOf(1.toBigInteger()..4.toBigInteger())
        assertEquals(input, sToDMap.get(input))
    }

    @Test
    fun testUnequalSourceAndDestination() {
        val testInput = listOf("10 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(10.toBigInteger(), sToDMap.get(1.toBigInteger()))
        assertEquals(11.toBigInteger(), sToDMap.get(2.toBigInteger()))
        assertEquals(12.toBigInteger(), sToDMap.get(3.toBigInteger()))
        assertEquals(4.toBigInteger(), sToDMap.get(4.toBigInteger()))
    }

    @Test
    fun testUnequalSourceAndDestinationRanges() {
        val testInput = listOf("10 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        val input = listOf(1.toBigInteger()..4.toBigInteger())
        val output = listOf(10.toBigInteger()..12.toBigInteger(), 4.toBigInteger()..4.toBigInteger())
        assertEquals(output, sToDMap.get(input))
    }

    @Test
    fun testFillsInTheGaps() {
        val testInput = listOf("10 3 3", "20 8 2")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(0.toBigInteger(), sToDMap.get(0.toBigInteger()))
        assertEquals(1.toBigInteger(), sToDMap.get(1.toBigInteger()))
        assertEquals(2.toBigInteger(), sToDMap.get(2.toBigInteger()))
        assertEquals(10.toBigInteger(), sToDMap.get(3.toBigInteger()))
        assertEquals(11.toBigInteger(), sToDMap.get(4.toBigInteger()))
        assertEquals(12.toBigInteger(), sToDMap.get(5.toBigInteger()))
        assertEquals(6.toBigInteger(), sToDMap.get(6.toBigInteger()))
        assertEquals(7.toBigInteger(), sToDMap.get(7.toBigInteger()))
        assertEquals(20.toBigInteger(), sToDMap.get(8.toBigInteger()))
        assertEquals(21.toBigInteger(), sToDMap.get(9.toBigInteger()))
        assertEquals(10.toBigInteger(), sToDMap.get(10.toBigInteger()))
    }

    @Test
    fun testTransformEntireRangeExactly() {
        val sourceRange = 1.toBigInteger()..4.toBigInteger()
        val transformationRange = 1.toBigInteger()..4.toBigInteger()
        val transformationFn = { input: BigInteger -> input + 10.toBigInteger() }
        val outputRanges = listOf(11.toBigInteger()..14.toBigInteger())
        assertEquals(outputRanges, transformRange(sourceRange, transformationRange, transformationFn))
    }

    @Test
    fun testTransformRangeEnd() {
        val sourceRange = 1.toBigInteger()..4.toBigInteger()
        val transformationRange = 3.toBigInteger()..6.toBigInteger()
        val transformationFn = { input: BigInteger -> input + 10.toBigInteger() }
        val outputRanges = listOf(1.toBigInteger()..2.toBigInteger(), 13.toBigInteger()..14.toBigInteger())
        assertEquals(outputRanges, transformRange(sourceRange, transformationRange, transformationFn))
    }

    @Test
    fun testTransformRangeStart() {
        val sourceRange = 1.toBigInteger()..4.toBigInteger()
        val transformationRange = 1.toBigInteger()..2.toBigInteger()
        val transformationFn = { input: BigInteger -> input + 10.toBigInteger() }
        val outputRanges = listOf(11.toBigInteger()..12.toBigInteger(), 3.toBigInteger()..4.toBigInteger())
        assertEquals(outputRanges, transformRange(sourceRange, transformationRange, transformationFn))
    }

    @Test
    fun testTransformRangeNoOverlap() {
        val sourceRange = 1.toBigInteger()..4.toBigInteger()
        val transformationRange = 5.toBigInteger()..8.toBigInteger()
        val transformationFn = { input: BigInteger -> input + 10.toBigInteger() }
        val outputRanges = listOf(1.toBigInteger()..4.toBigInteger())
        assertEquals(outputRanges, transformRange(sourceRange, transformationRange, transformationFn))
    }

    @Test
    fun testTransformEntireRange() {
        val sourceRange = 1.toBigInteger()..4.toBigInteger()
        val transformationRange = 0.toBigInteger()..5.toBigInteger()
        val transformationFn = { input: BigInteger -> input + 10.toBigInteger() }
        val outputRanges = listOf(11.toBigInteger()..14.toBigInteger())
        assertEquals(outputRanges, transformRange(sourceRange, transformationRange, transformationFn))
    }
}