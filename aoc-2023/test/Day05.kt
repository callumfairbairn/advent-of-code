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
    fun testUnequalSourceAndDestination() {
        val testInput = listOf("10 1 3")
        val sToDMap = SourceToDestinationMap(testInput)
        assertEquals(10.toBigInteger(), sToDMap.get(1.toBigInteger()))
        assertEquals(11.toBigInteger(), sToDMap.get(2.toBigInteger()))
        assertEquals(12.toBigInteger(), sToDMap.get(3.toBigInteger()))
        assertEquals(4.toBigInteger(), sToDMap.get(4.toBigInteger()))
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

}