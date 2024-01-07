import java.math.BigInteger
import kotlin.test.Test
import kotlin.test.assertEquals

internal class TestSourceToDestinationMap {
    @Test
    fun testOneToOneMap() {
        val testInput = listOf("1 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(BigInteger.valueOf(1), sToDMap.get(BigInteger.valueOf(1)))
        assertEquals(BigInteger.valueOf(2), sToDMap.get(BigInteger.valueOf(2)))
        assertEquals(BigInteger.valueOf(3), sToDMap.get(BigInteger.valueOf(3)))
        assertEquals(BigInteger.valueOf(4), sToDMap.get(BigInteger.valueOf(4)))
    }

    @Test
    fun testUnequalSourceAndDestination() {
        val testInput = listOf("10 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(BigInteger.valueOf(10), sToDMap.get(BigInteger.valueOf(1)))
        assertEquals(BigInteger.valueOf(11), sToDMap.get(BigInteger.valueOf(2)))
        assertEquals(BigInteger.valueOf(12), sToDMap.get(BigInteger.valueOf(3)))
        assertEquals(BigInteger.valueOf(4), sToDMap.get(BigInteger.valueOf(4)))
    }

    @Test
    fun testFillsInTheGaps() {
        val testInput = listOf("10 3 3", "20 8 2")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(BigInteger.valueOf(0), sToDMap.get(BigInteger.valueOf(0)))
        assertEquals(BigInteger.valueOf(1), sToDMap.get(BigInteger.valueOf(1)))
        assertEquals(BigInteger.valueOf(2), sToDMap.get(BigInteger.valueOf(2)))
        assertEquals(BigInteger.valueOf(10), sToDMap.get(BigInteger.valueOf(3)))
        assertEquals(BigInteger.valueOf(11), sToDMap.get(BigInteger.valueOf(4)))
        assertEquals(BigInteger.valueOf(12), sToDMap.get(BigInteger.valueOf(5)))
        assertEquals(BigInteger.valueOf(6), sToDMap.get(BigInteger.valueOf(6)))
        assertEquals(BigInteger.valueOf(7), sToDMap.get(BigInteger.valueOf(7)))
        assertEquals(BigInteger.valueOf(20), sToDMap.get(BigInteger.valueOf(8)))
        assertEquals(BigInteger.valueOf(21), sToDMap.get(BigInteger.valueOf(9)))
        assertEquals(BigInteger.valueOf(10), sToDMap.get(BigInteger.valueOf(10)))
    }

}